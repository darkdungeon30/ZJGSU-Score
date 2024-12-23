package org.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName Lesson
 */
@TableName(value ="Lesson")
@Data
public class Lesson implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer lid;

    /**
     * 
     */
    private String lname;

    /**
     * 
     */
    private String lteacher;

    /**
     * 
     */
    private String ldesc;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Lesson other = (Lesson) that;
        return (this.getLid() == null ? other.getLid() == null : this.getLid().equals(other.getLid()))
            && (this.getLname() == null ? other.getLname() == null : this.getLname().equals(other.getLname()))
            && (this.getLteacher() == null ? other.getLteacher() == null : this.getLteacher().equals(other.getLteacher()))
            && (this.getLdesc() == null ? other.getLdesc() == null : this.getLdesc().equals(other.getLdesc()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLid() == null) ? 0 : getLid().hashCode());
        result = prime * result + ((getLname() == null) ? 0 : getLname().hashCode());
        result = prime * result + ((getLteacher() == null) ? 0 : getLteacher().hashCode());
        result = prime * result + ((getLdesc() == null) ? 0 : getLdesc().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", lid=").append(lid);
        sb.append(", lname=").append(lname);
        sb.append(", lteacher=").append(lteacher);
        sb.append(", ldesc=").append(ldesc);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}