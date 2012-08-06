/*
 * Copyright 2003 - 2012 The eFaps Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Revision:        $Rev$
 * Last Changed:    $Date$
 * Last Changed By: $Author$
 */

package org.efaps.esjp.twoplan.jaxb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * TODO comment!
 *
 * @author The eFaps Team
 * @version $Id: $
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "Project")
@XmlType(name = "org.efaps.esjp.twoplan.jaxb.Project")
public class Project
    extends AbstractObject
{
    /**
     * Name of this project.
     */
    @XmlAttribute(name = "name")
    private String name;

    /**
     * Start date of this project.
     */
    @XmlAttribute(name = "startDate")
    private Date startDate;

    /**
     * Finish date of this project.
     */
    @XmlAttribute(name = "finishDate")
    private Date finishDate;

    /**
     * List of maps for this project.
     */
    @XmlElement(name = "maps")
    private final List<Maps> maps = new ArrayList<Maps>();

    /**
     * Calendar of this project.
     */
    @XmlElement(name = "calendar")
    private Calendar calendar;

    /**
     * List of workPackages for this project.
     */
    @XmlElement(name = "workPackages")
    private final List<WorkPackage> workPackages = new ArrayList<WorkPackage>();

    /**
     * Getter method for the instance variable {@link #workPackages}.
     *
     * @return value of instance variable {@link #workPackages}
     */
    public List<WorkPackage> getWorkPackages()
    {
        return this.workPackages;
    }

    /**
     * Getter method for the instance variable {@link #calendar}.
     *
     * @return value of instance variable {@link #calendar}
     */
    public Calendar getCalendar()
    {
        return this.calendar;
    }

    /**
     * Setter method for instance variable {@link #calendar}.
     *
     * @param _calendar value for instance variable {@link #calendar}
     */
    public void setCalendar(final Calendar _calendar)
    {
        this.calendar = _calendar;
    }

    /**
     * Getter method for the instance variable {@link #finishDate}.
     *
     * @return value of instance variable {@link #finishDate}
     */
    public Date getFinishDate()
    {
        return this.finishDate;
    }

    /**
     * Setter method for instance variable {@link #finishDate}.
     *
     * @param _finishDate value for instance variable {@link #finishDate}
     */
    public void setFinishDate(final Date _finishDate)
    {
        this.finishDate = _finishDate;
    }

    /**
     * Getter method for the instance variable {@link #maps}.
     *
     * @return value of instance variable {@link #maps}
     */
    public List<Maps> getMaps()
    {
        return this.maps;
    }

    /**
     * Getter method for the instance variable {@link #startDate}.
     *
     * @return value of instance variable {@link #startDate}
     */
    public Date getStartDate()
    {
        return this.startDate;
    }

    /**
     * Setter method for instance variable {@link #startDate}.
     *
     * @param _startDate value for instance variable {@link #startDate}
     */
    public void setStartDate(final Date _startDate)
    {
        this.startDate = _startDate;
    }

    /**
     * Getter method for the instance variable {@link #name}.
     *
     * @return value of instance variable {@link #name}
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Setter method for instance variable {@link #name}.
     *
     * @param _name value for instance variable {@link #name}
     */
    public void setName(final String _name)
    {
        this.name = _name;
    }
}
