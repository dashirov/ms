package org.maj.sm.model;

import org.maj.sm.utility.ChangeLogEntryComparator;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;


/**
 * Created by dashirov on 10/17/16.
 */
public class ChangeLog<T> {
    private SortedSet<ChangeLogEntry<T>> changeLog = new TreeSet<>(new ChangeLogEntryComparator());

    public SortedSet<ChangeLogEntry<T>> getChangeLogs() {
        return changeLog;
    }

    public void setChangeLog(SortedSet<ChangeLogEntry<T>> changeLog){
        this.changeLog=changeLog;
    }

    public T getValue(final Date date, T defaultValue){
        for (ChangeLogEntry<T> changeLogEntry: this.changeLog
                ) {
            if (changeLogEntry.getEffectiveDate().before(date) || changeLogEntry.getEffectiveDate().equals(date)){
                return changeLogEntry.getValue();
            }

        }
        return defaultValue;
    }

    public void addLogEntry(final ChangeLogEntry changeLogEntry){
        /**
         * No duplicate entries are allowed - keyed on effective date. New additions will replace conflicting items.
         */
        changeLog.removeIf(new Predicate<ChangeLogEntry<T>>() {
            @Override
            public boolean test(ChangeLogEntry<T> tChangeLogEntry) {
                if (tChangeLogEntry.getEffectiveDate().equals(changeLogEntry.getEffectiveDate()))
                   return true;
                else
                    return false;
            }
        });
        changeLog.add(changeLogEntry);
    }

    public ChangeLog() {
        super();
    }
}
