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
import javax.xml.bind.annotation.XmlType;

/**
 * TODO comment!
 *
 * @author The eFaps Team
 * @version $Id: $
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "org.efaps.esjp.twoplan.jaxb.WorkPackage")
public class WorkPackage
    extends AbstractObject
{
    /**
     * Name of this workpackage.
     */
    @XmlAttribute(name="name")
    private String name;

    /**
     * Description for this workpackage.
     */
    @XmlAttribute(name="description")
    private String description;

    /**
     * Type of this workpackage.
     */
    @XmlAttribute(name="type")
    private String type;

    /**
     * Start date of this workpackage.
     */
    @XmlAttribute(name="startDate")
    private Date startDate;

    /**
     * Finish date of this workpackage.
     */
    @XmlAttribute(name="finishDate")
    private Date finishDate;

    /**
     * List of child workpackages for this workpackage.
     */
    @XmlElement(name = "workPackages")
    private final List<WorkPackage> children = new ArrayList<WorkPackage>();

    /**
     * Getter method for the instance variable {@link #children}.
     *
     * @return value of instance variable {@link #children}
     */
    public List<WorkPackage> getChildren()
    {
        return this.children;
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

    /**
     * Getter method for the instance variable {@link #type}.
     *
     * @return value of instance variable {@link #type}
     */
    public String getType()
    {
        return this.type;
    }

    /**
     * Setter method for instance variable {@link #type}.
     *
     * @param _type value for instance variable {@link #type}
     */
    public void setType(final String _type)
    {
        this.type = _type;
    }

    /**
     * Getter method for the instance variable {@link #description}.
     *
     * @return value of instance variable {@link #description}
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * Setter method for instance variable {@link #description}.
     *
     * @param _description value for instance variable {@link #description}
     */
    public void setDescription(final String _description)
    {
        this.description = _description;
    }
}
