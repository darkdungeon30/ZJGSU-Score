package org.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName LessonUser
 */
@TableName(value ="LessonUser")
@Data
public class Lessonuser implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer luid;

    /**
     * 
     */
    private Integer uid;

    /**
     * 
     */
    private Integer lid;

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
        Lessonuser other = (Lessonuser) that;
        return (this.getLuid() == null ? other.getLuid() == null : this.getLuid().equals(other.getLuid()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getLid() == null ? other.getLid() == null : this.getLid().equals(other.getLid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLuid() == null) ? 0 : getLuid().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getLid() == null) ? 0 : getLid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", luid=").append(luid);
        sb.append(", uid=").append(uid);
        sb.append(", lid=").append(lid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}