/* Created by Kapoor and Smith */

package com.smith.kapoor.theeisenhowerwaytogtd.data.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.smith.kapoor.theeisenhowerwaytogtd.data.constants.CompletedEnum;
import com.smith.kapoor.theeisenhowerwaytogtd.data.constants.PriorityEnum;

import java.util.Date;


/**
 * Basic POJO class of a Task.  Parcel implimentation allows the passing of a TaskDO through a bundle.
 */
public class TaskDO implements Parcelable{

    private int id;
    private String name;
    private PriorityEnum priorityEnum;
    private String description;
    private Date completetionDate;
    private CompletedEnum completedEnum;

    public TaskDO() {
        id = -1;
    }

    protected TaskDO(Parcel in) {
        readFromParcel(in);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PriorityEnum getPriorityEnum() {
        return priorityEnum;
    }

    public void setPriorityEnum(PriorityEnum priorityEnum) {
        this.priorityEnum = priorityEnum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCompletetionDate() {
        return completetionDate;
    }

    public void setCompletetionDate(Date completetionDate) {
        this.completetionDate = completetionDate;
    }

    public CompletedEnum getCompletedEnum() {
        return completedEnum;
    }

    public void setCompletedEnum(CompletedEnum completedEnum) {
        this.completedEnum = completedEnum;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(priorityEnum.toString());
        dest.writeString(description);
        dest.writeLong(completetionDate.getTime());
        dest.writeString(completedEnum.toString());
    }


    public void readFromParcel(Parcel in) {
        setId(in.readInt());
        setName(in.readString());
        setPriorityEnum(PriorityEnum.valueOf(in.readString()));
        setDescription(in.readString());
        completetionDate = new Date(in.readLong());
        setCompletedEnum(CompletedEnum.valueOf(in.readString()));
    }

    public static final Parcelable.Creator<TaskDO> CREATOR = new
            Parcelable.Creator<TaskDO>() {
                public TaskDO createFromParcel(Parcel in) {
                    return new TaskDO(in);
                }

                public TaskDO[] newArray(int size) {
                    return new TaskDO[size];
                }
            };

}
