package org.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName Evaluate
 */
@TableName(value ="Evaluate")
@Data
public class Evaluate implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer eid;

    /**
     * 
     */
    private Integer uid;

    /**
     * 
     */
    private Integer lid;

    /**
     * 
     */
    private Integer especial;

    /**
     * 
     */
    private Integer escore;

    /**
     * 
     */
    private Integer edifficult;

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
        Evaluate other = (Evaluate) that;
        return (this.getEid() == null ? other.getEid() == null : this.getEid().equals(other.getEid()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getLid() == null ? other.getLid() == null : this.getLid().equals(other.getLid()))
            && (this.getEspecial() == null ? other.getEspecial() == null : this.getEspecial().equals(other.getEspecial()))
            && (this.getEscore() == null ? other.getEscore() == null : this.getEscore().equals(other.getEscore()))
            && (this.getEdifficult() == null ? other.getEdifficult() == null : this.getEdifficult().equals(other.getEdifficult()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getEid() == null) ? 0 : getEid().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getLid() == null) ? 0 : getLid().hashCode());
        result = prime * result + ((getEspecial() == null) ? 0 : getEspecial().hashCode());
        result = prime * result + ((getEscore() == null) ? 0 : getEscore().hashCode());
        result = prime * result + ((getEdifficult() == null) ? 0 : getEdifficult().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", eid=").append(eid);
        sb.append(", uid=").append(uid);
        sb.append(", lid=").append(lid);
        sb.append(", especial=").append(especial);
        sb.append(", escore=").append(escore);
        sb.append(", edifficult=").append(edifficult);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}