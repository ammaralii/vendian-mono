package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.PerformanceCycles20190826} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.PerformanceCycles20190826Resource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /performance-cycles-20190826-s?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PerformanceCycles20190826Criteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter month;

    private BooleanFilter year;

    private BooleanFilter totalresources;

    private BooleanFilter pmreviewed;

    private BooleanFilter archreviewed;

    private InstantFilter startdate;

    private InstantFilter enddate;

    private BooleanFilter isactive;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private BooleanFilter projectcount;

    private IntegerFilter criteria;

    private BooleanFilter notificationsent;

    private InstantFilter duedate;

    private InstantFilter initiationdate;

    private Boolean distinct;

    public PerformanceCycles20190826Criteria() {}

    public PerformanceCycles20190826Criteria(PerformanceCycles20190826Criteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.month = other.month == null ? null : other.month.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.totalresources = other.totalresources == null ? null : other.totalresources.copy();
        this.pmreviewed = other.pmreviewed == null ? null : other.pmreviewed.copy();
        this.archreviewed = other.archreviewed == null ? null : other.archreviewed.copy();
        this.startdate = other.startdate == null ? null : other.startdate.copy();
        this.enddate = other.enddate == null ? null : other.enddate.copy();
        this.isactive = other.isactive == null ? null : other.isactive.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.projectcount = other.projectcount == null ? null : other.projectcount.copy();
        this.criteria = other.criteria == null ? null : other.criteria.copy();
        this.notificationsent = other.notificationsent == null ? null : other.notificationsent.copy();
        this.duedate = other.duedate == null ? null : other.duedate.copy();
        this.initiationdate = other.initiationdate == null ? null : other.initiationdate.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PerformanceCycles20190826Criteria copy() {
        return new PerformanceCycles20190826Criteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getMonth() {
        return month;
    }

    public BooleanFilter month() {
        if (month == null) {
            month = new BooleanFilter();
        }
        return month;
    }

    public void setMonth(BooleanFilter month) {
        this.month = month;
    }

    public BooleanFilter getYear() {
        return year;
    }

    public BooleanFilter year() {
        if (year == null) {
            year = new BooleanFilter();
        }
        return year;
    }

    public void setYear(BooleanFilter year) {
        this.year = year;
    }

    public BooleanFilter getTotalresources() {
        return totalresources;
    }

    public BooleanFilter totalresources() {
        if (totalresources == null) {
            totalresources = new BooleanFilter();
        }
        return totalresources;
    }

    public void setTotalresources(BooleanFilter totalresources) {
        this.totalresources = totalresources;
    }

    public BooleanFilter getPmreviewed() {
        return pmreviewed;
    }

    public BooleanFilter pmreviewed() {
        if (pmreviewed == null) {
            pmreviewed = new BooleanFilter();
        }
        return pmreviewed;
    }

    public void setPmreviewed(BooleanFilter pmreviewed) {
        this.pmreviewed = pmreviewed;
    }

    public BooleanFilter getArchreviewed() {
        return archreviewed;
    }

    public BooleanFilter archreviewed() {
        if (archreviewed == null) {
            archreviewed = new BooleanFilter();
        }
        return archreviewed;
    }

    public void setArchreviewed(BooleanFilter archreviewed) {
        this.archreviewed = archreviewed;
    }

    public InstantFilter getStartdate() {
        return startdate;
    }

    public InstantFilter startdate() {
        if (startdate == null) {
            startdate = new InstantFilter();
        }
        return startdate;
    }

    public void setStartdate(InstantFilter startdate) {
        this.startdate = startdate;
    }

    public InstantFilter getEnddate() {
        return enddate;
    }

    public InstantFilter enddate() {
        if (enddate == null) {
            enddate = new InstantFilter();
        }
        return enddate;
    }

    public void setEnddate(InstantFilter enddate) {
        this.enddate = enddate;
    }

    public BooleanFilter getIsactive() {
        return isactive;
    }

    public BooleanFilter isactive() {
        if (isactive == null) {
            isactive = new BooleanFilter();
        }
        return isactive;
    }

    public void setIsactive(BooleanFilter isactive) {
        this.isactive = isactive;
    }

    public InstantFilter getCreatedat() {
        return createdat;
    }

    public InstantFilter createdat() {
        if (createdat == null) {
            createdat = new InstantFilter();
        }
        return createdat;
    }

    public void setCreatedat(InstantFilter createdat) {
        this.createdat = createdat;
    }

    public InstantFilter getUpdatedat() {
        return updatedat;
    }

    public InstantFilter updatedat() {
        if (updatedat == null) {
            updatedat = new InstantFilter();
        }
        return updatedat;
    }

    public void setUpdatedat(InstantFilter updatedat) {
        this.updatedat = updatedat;
    }

    public BooleanFilter getProjectcount() {
        return projectcount;
    }

    public BooleanFilter projectcount() {
        if (projectcount == null) {
            projectcount = new BooleanFilter();
        }
        return projectcount;
    }

    public void setProjectcount(BooleanFilter projectcount) {
        this.projectcount = projectcount;
    }

    public IntegerFilter getCriteria() {
        return criteria;
    }

    public IntegerFilter criteria() {
        if (criteria == null) {
            criteria = new IntegerFilter();
        }
        return criteria;
    }

    public void setCriteria(IntegerFilter criteria) {
        this.criteria = criteria;
    }

    public BooleanFilter getNotificationsent() {
        return notificationsent;
    }

    public BooleanFilter notificationsent() {
        if (notificationsent == null) {
            notificationsent = new BooleanFilter();
        }
        return notificationsent;
    }

    public void setNotificationsent(BooleanFilter notificationsent) {
        this.notificationsent = notificationsent;
    }

    public InstantFilter getDuedate() {
        return duedate;
    }

    public InstantFilter duedate() {
        if (duedate == null) {
            duedate = new InstantFilter();
        }
        return duedate;
    }

    public void setDuedate(InstantFilter duedate) {
        this.duedate = duedate;
    }

    public InstantFilter getInitiationdate() {
        return initiationdate;
    }

    public InstantFilter initiationdate() {
        if (initiationdate == null) {
            initiationdate = new InstantFilter();
        }
        return initiationdate;
    }

    public void setInitiationdate(InstantFilter initiationdate) {
        this.initiationdate = initiationdate;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PerformanceCycles20190826Criteria that = (PerformanceCycles20190826Criteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(month, that.month) &&
            Objects.equals(year, that.year) &&
            Objects.equals(totalresources, that.totalresources) &&
            Objects.equals(pmreviewed, that.pmreviewed) &&
            Objects.equals(archreviewed, that.archreviewed) &&
            Objects.equals(startdate, that.startdate) &&
            Objects.equals(enddate, that.enddate) &&
            Objects.equals(isactive, that.isactive) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(projectcount, that.projectcount) &&
            Objects.equals(criteria, that.criteria) &&
            Objects.equals(notificationsent, that.notificationsent) &&
            Objects.equals(duedate, that.duedate) &&
            Objects.equals(initiationdate, that.initiationdate) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            month,
            year,
            totalresources,
            pmreviewed,
            archreviewed,
            startdate,
            enddate,
            isactive,
            createdat,
            updatedat,
            projectcount,
            criteria,
            notificationsent,
            duedate,
            initiationdate,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerformanceCycles20190826Criteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (month != null ? "month=" + month + ", " : "") +
            (year != null ? "year=" + year + ", " : "") +
            (totalresources != null ? "totalresources=" + totalresources + ", " : "") +
            (pmreviewed != null ? "pmreviewed=" + pmreviewed + ", " : "") +
            (archreviewed != null ? "archreviewed=" + archreviewed + ", " : "") +
            (startdate != null ? "startdate=" + startdate + ", " : "") +
            (enddate != null ? "enddate=" + enddate + ", " : "") +
            (isactive != null ? "isactive=" + isactive + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (projectcount != null ? "projectcount=" + projectcount + ", " : "") +
            (criteria != null ? "criteria=" + criteria + ", " : "") +
            (notificationsent != null ? "notificationsent=" + notificationsent + ", " : "") +
            (duedate != null ? "duedate=" + duedate + ", " : "") +
            (initiationdate != null ? "initiationdate=" + initiationdate + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
