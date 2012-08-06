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
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.efaps.admin.program.esjp.EFapsRevision;
import org.efaps.admin.program.esjp.EFapsUUID;

/**
 * TODO comment!
 *
 * @author The eFaps Team
 * @version $Id: $
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "org.efaps.esjp.twoplan.jaxb.Maps")
@EFapsUUID("e6e27fb7-2a9c-4071-b999-ebf93741c2e6")
@EFapsRevision("$Rev: 5526 $")
public class Maps
{
    /**
     * Type of this Map.
     */
    @XmlAttribute(name="type")
    private String type;

    /**
     * List of entries.
     */
    @XmlElement(name = "entries")
    private final List<Entries> entries = new ArrayList();

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
     * Getter method for the instance variable {@link #entries}.
     *
     * @return value of instance variable {@link #entries}
     */
    public List<Entries> getEntries()
    {
        return this.entries;
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
}
