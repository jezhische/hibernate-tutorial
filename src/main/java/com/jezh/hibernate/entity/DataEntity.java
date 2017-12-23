package com.jezh.hibernate.entity;

import com.jezh.hibernate.util.DataUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.Date;

@Entity
@Table
public class DataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column/*(unique = true, updatable = false)*/
    private String tname;

    @Temporal(TemporalType.DATE)
    @Column
    private Date someDate;

    @NotNull
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime(3) DEFAULT CURRENT_TIMESTAMP(3)")
    private String timeStamp;

//    todo: NB: without defaul constructor I cannot retrieve this object from database
    public DataEntity() {
    }

    public DataEntity(String tname, Date someDate) {
        this.tname = tname;
        this.someDate = someDate;
//        timeStamp = new Date();
        timeStamp = DataUtils.formatDateToString(new Date(), DataUtils.timeFormatter);
    }

    public DataEntity(String tname, String someDate) {
        this.tname = tname;
        try {
            this.someDate = DataUtils.parseDateFromString(someDate, DataUtils.dateFormatter);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        timeStamp = DataUtils.formatDateToString(new Date(), DataUtils.timeFormatter);
//        timeStamp = new Date(); // Calendar.getInstance().getTime()
//        timeStamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
    }

    public long getId() {
        return id;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public Date getSomeDate() {
        return someDate;
    }

    public void setSomeDate(Date someDate) {
        this.someDate = someDate;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

//    public void setTimeStamp(Date timeStamp) {
//        this.timeStamp = timeStamp;
//    }

    @Override
    public String toString() {
        return "DataEntity{" +
                "id=" + id +
                ", tname='" + tname + '\'' +
                ", someDate='" + DataUtils.formatDateToString(someDate, DataUtils.dateFormatter) + '\'' +
                ", timeStamp='" + timeStamp/*DataUtils.formatDateToString(timeStamp, DataUtils.timeFormatter)*/ + '\'' +
                '}';
    }

//    https://vladmihalcea.com/2013/10/23/hibernate-facts-equals-and-hashcode/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof DataEntity)) return false;
        DataEntity that = (DataEntity) o;
        return new EqualsBuilder()
//                .reflectionAppend(tname, that.tname) // It uses AccessibleObject.setAccessible to gain access
// to private fields. This means that it will throw a security exception if run under a security manager,
// if the permissions are not set up correctly.
                .appendSuper(super.equals(that))
                .append(tname, that.tname)
                .append(someDate, that.someDate)
                .append(timeStamp, that.timeStamp)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(tname)
                .append(someDate)
                .append(timeStamp)
                .toHashCode();
    }
}
