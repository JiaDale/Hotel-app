package com.jdy.hotel.data.Beans;


import android.os.Build;

import com.jdy.hotel.utils.CollectionUtils;
import com.jdy.hotel.utils.StringFunction;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/18 18:06
 */
class ChangeDate implements Variable {
    private final Collection<ChangeRecord> records;

    ChangeDate(String key, Object organValue, Object value) {
        records = CollectionUtils.newHashSet(new ChangeRecord(key, organValue, value));
    }

    public void add(String key, Object organValue, Object value) {
        records.add(new ChangeRecord(key, organValue, value));
    }

    @Override
    public String toString() {
        Appendable appendable = new StringBuilder();
        try {
            appendable.append('{');
            Iterator<ChangeRecord> iterator = records.iterator();
            while (iterator.hasNext()) {
                appendable.append(iterator.next().toString());
                if (iterator.hasNext()) {
                    appendable.append(',');
                }
            }
            return appendable.append('}').toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public boolean hasChanged() {
        return !records.isEmpty();
    }

    @Override
    public ChangeDate getChangeData() {
        return this;
    }

    private class ChangeRecord {
        private final String key;
        private final Object organValue, changeValue;

        ChangeRecord(String key, Object organValue, Object value) {
            this.key = key;
            this.organValue = organValue;
            changeValue = value;
        }

        @Override
        public String toString() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return "[key : " + key + ", value: " + StringFunction.getInstance().apply(organValue) + "  --> " + StringFunction.getInstance().apply(changeValue) + "]";
            }
            return "[key : " + key + ", value: " +  organValue.toString()  + "  --> " + changeValue.toString() + "]";
        }
    }
}
