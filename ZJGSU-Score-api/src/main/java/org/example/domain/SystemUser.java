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
    @TableId
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
            && (this.getUtype() == null ? other.getUtype() == null : this.getUtype().equals(other.getUtype()));
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
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}