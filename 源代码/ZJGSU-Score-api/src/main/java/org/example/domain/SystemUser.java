package org.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName System_User
 */
@TableName(value ="System_User")
@Data
public class SystemUser implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer uid;

    /**
     * 
     */
    private Integer uaccount;

    /**
     * 
     */
    private String uname;

    /**
     * 
     */
    private String upassword;

    /**
     * 
     */
    private Integer utype;

    /**
     * 
     */
    private Integer uspecial;

    /**
     * 
     */
    private Integer uscore;

    /**
     * 
     */
    private Integer udifficult;

    /**
     * 
     */
    private Integer uexam;

    /**
     * 
     */
    private Integer ufun;

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
        SystemUser other = (SystemUser) that;
        return (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getUaccount() == null ? other.getUaccount() == null : this.getUaccount().equals(other.getUaccount()))
            && (this.getUname() == null ? other.getUname() == null : this.getUname().equals(other.getUname()))
            && (this.getUpassword() == null ? other.getUpassword() == null : this.getUpassword().equals(other.getUpassword()))
            && (this.getUtype() == null ? other.getUtype() == null : this.getUtype().equals(other.getUtype()))
            && (this.getUspecial() == null ? other.getUspecial() == null : this.getUspecial().equals(other.getUspecial()))
            && (this.getUscore() == null ? other.getUscore() == null : this.getUscore().equals(other.getUscore()))
            && (this.getUdifficult() == null ? other.getUdifficult() == null : this.getUdifficult().equals(other.getUdifficult()))
            && (this.getUexam() == null ? other.getUexam() == null : this.getUexam().equals(other.getUexam()))
            && (this.getUfun() == null ? other.getUfun() == null : this.getUfun().equals(other.getUfun()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getUaccount() == null) ? 0 : getUaccount().hashCode());
        result = prime * result + ((getUname() == null) ? 0 : getUname().hashCode());
        result = prime * result + ((getUpassword() == null) ? 0 : getUpassword().hashCode());
        result = prime * result + ((getUtype() == null) ? 0 : getUtype().hashCode());
        result = prime * result + ((getUspecial() == null) ? 0 : getUspecial().hashCode());
        result = prime * result + ((getUscore() == null) ? 0 : getUscore().hashCode());
        result = prime * result + ((getUdifficult() == null) ? 0 : getUdifficult().hashCode());
        result = prime * result + ((getUexam() == null) ? 0 : getUexam().hashCode());
        result = prime * result + ((getUfun() == null) ? 0 : getUfun().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", uid=").append(uid);
        sb.append(", uaccount=").append(uaccount);
        sb.append(", uname=").append(uname);
        sb.append(", upassword=").append(upassword);
        sb.append(", utype=").append(utype);
        sb.append(", uspecial=").append(uspecial);
        sb.append(", uscore=").append(uscore);
        sb.append(", udifficult=").append(udifficult);
        sb.append(", uexam=").append(uexam);
        sb.append(", ufun=").append(ufun);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}