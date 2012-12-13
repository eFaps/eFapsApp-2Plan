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

package org.efaps.twoplan.test;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
import org.efaps.esjp.twoplan.jaxb.AbstractObject;
import org.efaps.esjp.twoplan.jaxb.Calendar;
import org.efaps.esjp.twoplan.jaxb.Entries;
import org.efaps.esjp.twoplan.jaxb.Maps;
import org.efaps.esjp.twoplan.jaxb.Project;
import org.efaps.esjp.twoplan.jaxb.WorkPackage;
import org.joda.time.DateTime;
import org.testng.annotations.Test;
/**
 * TODO comment!
 *
 * @author The eFaps Team
 * @version $Id$
 */
public class ProjectTest
{

    @Test
    public void Marschal()
    {

        try {
            final Project project = new Project();
            project.setName("TestName");
            project.setContext("manual");
            project.setUuid("_-R1qgN9eEeGFYMDgrVHNxA");
            project.setStartDate(new DateTime());
            project.setFinishDate(new DateTime().plusMonths(1));

            final Calendar cal = new Calendar();
            cal.setContext("manual");
            cal.setUuid("__E2b0N9eEeGFYMDgrVHNxA");
            project.setCalendar(cal);

            final Maps map1 = new Maps();
            map1.setType("net.intime.crud.xml.map.path");

            final Entries entry = new Entries();
            entry.setKey("path");
            entry.setValue("demo");
            map1.getEntries().add(entry);
            project.getMaps().add(map1);

            final Maps map2 = new Maps();
            map2.setType("manual");

            final Entries entry2 = new Entries();
            entry2.setKey("net.intime.product.mapentry.standardWorkPackageType");
            entry2.setValue("2");
            map2.getEntries().add(entry2);
            project.getMaps().add(map2);

            final WorkPackage workPackage = new WorkPackage();
            workPackage.setName("WorkPackDemo1");
            workPackage.setContext("manual");
            workPackage.setUuid("_C0H6MN9wEeGFYMDgrVHNxA");
            workPackage.setDescription("hier ist der Text");
            workPackage.setType("manual");
            workPackage.setStartDate(new DateTime());
            workPackage.setFinishDate(new DateTime().plusWeeks(1));
            project.getWorkPackages().add(workPackage);

            final WorkPackage workPackage2 = new WorkPackage();
            workPackage2.setName("WorkPackDemo2");
            workPackage2.setContext("manual");
            workPackage2.setUuid("_C0H6MN9wE123YMDgrVHNxA");
            workPackage2.setDescription("hier ist der Text  nochmal");
            workPackage2.setType("manual");
            workPackage2.setStartDate(new DateTime().plusDays(2));
            workPackage2.setFinishDate(new DateTime().plusWeeks(2));
            project.getWorkPackages().add(workPackage2);


            final JAXBContext jc = JAXBContext.newInstance(AbstractObject.class, Calendar.class, Entries.class,
                            Maps.class, Project.class, WorkPackage.class, DateTime.class);
            final Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(project, System.out);
        } catch (final PropertyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void unmarschall()
    {
        final File file = FileUtils.getFile("src","test", "resources", "2plan.prj");
        try {
            if (file.exists()) {
                final JAXBContext jc = JAXBContext.newInstance(AbstractObject.class, Calendar.class, Entries.class,
                                Maps.class, Project.class, WorkPackage.class, DateTime.class);
                final Unmarshaller unmarshaller = jc.createUnmarshaller();
                final Object object = unmarshaller.unmarshal(file);
                if (object instanceof Project)
                {

                }
            }
        } catch (final JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
