package com.jdy.hotel.data.Beans;


import android.os.Build;

import androidx.annotation.RequiresApi;

import com.jdy.hotel.utils.BooleanFunction;
import com.jdy.hotel.utils.CollectionUtils;
import com.jdy.hotel.utils.DateFunction;
import com.jdy.hotel.utils.StringFunction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BaseEntity extends AbstractEntity implements BasicGetter<String>, Variable {

    private static final long serialVersionUID = 720782987071826141L;

    private final OrganData organData;

    public BaseEntity() {
        this(new ConcurrentHashMap<>());
    }

    public BaseEntity(Map<String, Object> map) {
        super(map);
        organData = new OrganData(map);
        subscribe(organData);
    }

    private static final String PRIMARY_KEY = "RowGuid";
    private static final String ROW_INDEX = "RowIndex";

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getRowGuid() {
        return getStr(PRIMARY_KEY);
    }

    public int getRowIndex() {
        return getInt(ROW_INDEX);
    }

    /**
     * 数据默认主键， 可以通过{@link UUID}生成
     *
     * @param rowGuid 主键值
     */
    public void setRowGuid(final String rowGuid) {
        set(PRIMARY_KEY, rowGuid);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public String getStr(String key) {
        return StringFunction.getInstance().apply(get(key));
    }

    @Override
    public Byte getByte(String key) {
        return get(key, null);
    }

    @Override
    public Short getShort(String key) {
        return get(key, null);
    }

    @Override
    public Integer getInt(String key) {
        return get(key, null);
    }

    @Override
    public Long getLong(String key) {
        return get(key, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Boolean getBoolean(String key) {
        return BooleanFunction.getInstance().apply(get(key));
    }

    @Override
    public Character getChar(String key) {
        return get(key, null);
    }

    @Override
    public Float getFloat(String key) {
        return get(key, 0f);
    }

    @Override
    public Double getDouble(String key) {
        return get(key, 0d);
    }

    @Override
    public BigDecimal getBigDecimal(String key) {
        return get(key, null);
    }

    @Override
    public BigInteger getBigInteger(String key) {
        return get(key, null);
    }

    @Override
    public <E extends Enum<E>> E getEnum(Class<E> clazz, String key) {
        throw new UnsupportedOperationException("此方法暂时不支持！");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Date getDate(String key) {
        return DateFunction.getInstance().apply(get(key));
    }

    void set(Map<String, Object> map) {
        if (CollectionUtils.isEmpty(map)) return;
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            set(next.getKey(), next.getValue());
        }
    }

    public void set(Entity entity) {
        set(entity.getDataMap());
    }

    public String[] getColumnNames() {
        Map<String, Object> dataMap = getDataMap();
        if (null == dataMap)
            return null;
        Set<String> keySet = dataMap.keySet();
        return keySet.toArray(new String[0]);
    }

    public Object[] getColumnValues() {
        Map<String, Object> dataMap = getDataMap();
        if (null == dataMap)
            return null;
        return dataMap.values().toArray();
    }

    @Override
    public boolean hasChanged() {
        return organData.changeDate.hasChanged();
    }

    @Override
    public ChangeDate getChangeData() {
        return organData.changeDate;
    }

    private class OrganData implements Serializable, Observer<String, Object> {
        private static final long serialVersionUID = 7893149586292102940L;

        private Map<String, Object> organMap;
        private ChangeDate changeDate;

        OrganData(Map<String, Object> organMap) {
            this.organMap = new ConcurrentHashMap<>(organMap);
        }

        @Override
        public String toString() {
            return organMap.toString();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void update(String key, Object value) {
            Object organValue = organMap.get(key);
            if (Objects.isNull(organValue)) {
                if (Objects.isNull(value))
                    return;
                collect(key, null, value);
                return;
            }
            if (organValue.equals(value))
                return;
            collect(key, organValue, value);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        private void collect(String key, Object organValue, Object value) {
            if (Objects.isNull(changeDate)) {
                changeDate = new ChangeDate(key, organValue, value);
            } else {
                changeDate.add(key, organValue, value);
            }
        }
    }
}
