package org.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

@TableName(value ="Evaluate")
@Data
public class Evaluate implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer eid;

    private Integer uid;

    private Integer lid;

    private Integer especial;

    private Integer escore;

    private Integer edifficult;

    private Integer echeckingin;

    private Integer ework;

    private Integer eexam;

    private Integer efun;

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getLid() {
        return lid;
    }

    public void setLid(Integer lid) {
        this.lid = lid;
    }

    public Integer getEspecial() {
        return especial;
    }

    public void setEspecial(Integer especial) {
        this.especial = especial;
    }

    public Integer getEscore() {
        return escore;
    }

    public void setEscore(Integer escore) {
        this.escore = escore;
    }

    public Integer getEdifficult() {
        return edifficult;
    }

    public void setEdifficult(Integer edifficult) {
        this.edifficult = edifficult;
    }

    public Integer getEcheckingin() {
        return echeckingin;
    }

    public void setEcheckingin(Integer echeckingin) {
        this.echeckingin = echeckingin;
    }

    public Integer getEwork() {
        return ework;
    }

    public void setEwork(Integer ework) {
        this.ework = ework;
    }

    public Integer getEexam() {
        return eexam;
    }

    public void setEexam(Integer eexam) {
        this.eexam = eexam;
    }

    public Integer getEfun() {
        return efun;
    }

    public void setEfun(Integer efun) {
        this.efun = efun;
    }

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
            && (this.getEdifficult() == null ? other.getEdifficult() == null : this.getEdifficult().equals(other.getEdifficult()))
            && (this.getEcheckingin() == null ? other.getEcheckingin() == null : this.getEcheckingin().equals(other.getEcheckingin()))
            && (this.getEwork() == null ? other.getEwork() == null : this.getEwork().equals(other.getEwork()))
            && (this.getEexam() == null ? other.getEexam() == null : this.getEexam().equals(other.getEexam()))
            && (this.getEfun() == null ? other.getEfun() == null : this.getEfun().equals(other.getEfun()));
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
        result = prime * result + ((getEcheckingin() == null) ? 0 : getEcheckingin().hashCode());
        result = prime * result + ((getEwork() == null) ? 0 : getEwork().hashCode());
        result = prime * result + ((getEexam() == null) ? 0 : getEexam().hashCode());
        result = prime * result + ((getEfun() == null) ? 0 : getEfun().hashCode());
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
        sb.append(", echeckingin=").append(echeckingin);
        sb.append(", ework=").append(ework);
        sb.append(", eexam=").append(eexam);
        sb.append(", efun=").append(efun);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
