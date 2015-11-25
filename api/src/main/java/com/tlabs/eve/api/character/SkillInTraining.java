package com.tlabs.eve.api.character;



import com.tlabs.eve.api.EveAPI;
import com.tlabs.eve.api.Skill;

import java.io.Serializable;
import java.util.Map;

public class SkillInTraining extends Skill implements Serializable {

    private static final long serialVersionUID = 1194595186724600630L;

    public enum Type {
        REQUIRED, QUEUE, PLAN, COMPLETED
    }

    //NOT in XML but much too convenient for clients to pass up
    private Type trainingType = Type.QUEUE;

    private long trainingEndTime;
    private long trainingStartTime;

    private long startSkillPoints;
    private long endSkillPoints;

    private int trainingLevel;

    public SkillInTraining() {
        super();
    }

    public SkillInTraining(final Skill s, final int level) {
        super(s);
        this.endSkillPoints = 0;
        this.startSkillPoints = 0;
        this.trainingEndTime = 0;
        this.trainingLevel = level;
        this.trainingStartTime = 0;
    }

    public long getEndTime() {
        return trainingEndTime;
    }

    public void setEndTime(long trainingEndTime) {
        this.trainingEndTime = trainingEndTime;
    }

    public void setEndTime(String trainingEndTime) {
        this.trainingEndTime = EveAPI.parseDateTime(trainingEndTime);
    }

    public long getStartTime() {
        return trainingStartTime;
    }

    public void setStartTime(long trainingStartTime) {
        this.trainingStartTime = trainingStartTime;
    }

    public final long getDuration() {
        final long now = System.currentTimeMillis();
        if (this.trainingStartTime < now) {
            return Math.max(0, this.trainingEndTime - now);
        }
        return this.trainingEndTime - this.trainingStartTime;
    }

    public long getStartSkillPoints() {
        return startSkillPoints;
    }

    public void setStartSkillPoints(long startSkillPoints) {
        this.startSkillPoints = startSkillPoints;
    }

    public long getEndSkillPoints() {
        return endSkillPoints;
    }

    public void setEndSkillPoints(long endSkillPoints) {
        this.endSkillPoints = endSkillPoints;
    }

    public int getSkillLevel() {
        return trainingLevel;
    }

    public void setSkillLevel(int trainingLevel) {
        this.trainingLevel = trainingLevel;
    }

    public final Type getType() {
        return trainingType;
    }

    public final void setType(Type trainingType) {
        this.trainingType = trainingType;
    }

    public final boolean requires(final SkillInTraining t) {
        if (t.getSkillID() == this.getSkillID()) {
            return this.getSkillLevel() > t.getSkillLevel();
        }

        final Map<Long, Integer> req = this.getRequiredSkills();
        for (long id : req.keySet()) {
            if (id == t.getSkillID()) {
                if (req.get(id) >= t.getSkillLevel()) {
                    return true;
                }
            }
        }
        return false;
    }
}
