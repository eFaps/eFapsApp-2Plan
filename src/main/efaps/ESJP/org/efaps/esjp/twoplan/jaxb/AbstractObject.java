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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import org.efaps.admin.program.esjp.EFapsRevision;
import org.efaps.admin.program.esjp.EFapsUUID;


/**
 * TODO comment!
 *
 * @author The eFaps Team
 * @version $Id$
 */
@XmlAccessorType(XmlAccessType.NONE)
@EFapsUUID("7960c579-be7b-4dd2-bad8-1570b7973ed2")
@EFapsRevision("$Rev$")
public class AbstractObject
{
    /**
     * UUID for this Object.
     */
    @XmlAttribute(name="UUID")
    private String uuid;

    /**
     * Context for this object.
     */
    @XmlAttribute(name="context")
    private String context;

    /**
     * Getter method for the instance variable {@link #context}.
     *
     * @return value of instance variable {@link #context}
     */
    public String getContext()
    {
        return this.context;
    }

    /**
     * Setter method for instance variable {@link #context}.
     *
     * @param _context value for instance variable {@link #context}
     */
    public void setContext(final String _context)
    {
        this.context = _context;
    }

    /**
     * Getter method for the instance variable {@link #uuid}.
     *
     * @return value of instance variable {@link #uuid}
     */
    public String getUuid()
    {
        return this.uuid;
    }

    /**
     * Setter method for instance variable {@link #uuid}.
     *
     * @param _uuid value for instance variable {@link #uuid}
     */
    public void setUuid(final String _uuid)
    {
        this.uuid = _uuid;
    }
}
