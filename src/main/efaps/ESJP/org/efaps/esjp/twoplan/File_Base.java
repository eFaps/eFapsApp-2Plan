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

package org.efaps.esjp.twoplan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

import org.efaps.admin.datamodel.Status;
import org.efaps.admin.event.Parameter;
import org.efaps.admin.event.Return;
import org.efaps.admin.event.Return.ReturnValues;
import org.efaps.admin.program.esjp.EFapsRevision;
import org.efaps.admin.program.esjp.EFapsUUID;
import org.efaps.db.Context;
import org.efaps.db.Context.FileParameter;
import org.efaps.db.Insert;
import org.efaps.db.Instance;
import org.efaps.db.InstanceQuery;
import org.efaps.db.MultiPrintQuery;
import org.efaps.db.PrintQuery;
import org.efaps.db.QueryBuilder;
import org.efaps.db.Update;
import org.efaps.esjp.ci.CIFormTwoPlan;
import org.efaps.esjp.ci.CIProjects;
import org.efaps.esjp.ci.CITwoPlan;
import org.efaps.esjp.common.file.FileUtil;
import org.efaps.esjp.twoplan.jaxb.AbstractObject;
import org.efaps.esjp.twoplan.jaxb.Calendar;
import org.efaps.esjp.twoplan.jaxb.Entries;
import org.efaps.esjp.twoplan.jaxb.Maps;
import org.efaps.esjp.twoplan.jaxb.Project;
import org.efaps.esjp.twoplan.jaxb.WorkPackage;
import org.efaps.util.EFapsException;
import org.joda.time.DateTime;

/**
 * TODO comment!
 *
 * @author The eFaps Team
 * @version $Id$
 */
@EFapsUUID("873327ed-7c6a-498b-9251-ad04de4c0fca")
@EFapsRevision("$Rev$")
public abstract class File_Base
{

    /**
     * @param _parameter     Parameter as passed by the eFaps API
     * @return new Return
     * @throws EFapsException on error
     */
    public Return checkinPostTrigger(final Parameter _parameter)
        throws EFapsException
    {
        try {
            final Map<String, FileParameter> filePara = Context.getThreadContext().getFileParameters();
            final FileParameter file = filePara.get(CIFormTwoPlan.TwoPlan_FileForm.upload.name);
            final InputStream input = file.getInputStream();

            final JAXBContext jc = JAXBContext.newInstance(Project.class, Maps.class, Entries.class, WorkPackage.class,
                            Calendar.class);
            final Unmarshaller unmarshaller = jc.createUnmarshaller();
            final Object object = unmarshaller.unmarshal(input);

            if (object instanceof Project) {
                final Instance fileInst = _parameter.getInstance();

                final PrintQuery print = new PrintQuery(fileInst);
                print.addAttribute(CITwoPlan.File.ProjectLink);
                print.execute();
                final Long projectId = print.<Long>getAttribute(CITwoPlan.File.ProjectLink);

                final Project project = (Project) object;
                // update/insert the workpackages as tasks
                for (final WorkPackage wp : project.getWorkPackages()) {
                    updateTask(_parameter, projectId, wp, null);
                }

                //set the status for the existing files
                final QueryBuilder queryBldr = new QueryBuilder(CITwoPlan.File);
                queryBldr.addWhereAttrEqValue(CITwoPlan.File.ProjectLink, projectId);
                queryBldr.addWhereAttrEqValue(CITwoPlan.File.Status,
                                Status.find(CITwoPlan.FileStatus.uuid, "Current").getId());
                queryBldr.addWhereAttrNotEqValue(CITwoPlan.File.ID, fileInst.getId());
                final InstanceQuery query = queryBldr.getQuery();
                query.execute();
                while (query.next()) {
                    final Update update = new Update(query.getCurrentValue());
                    update.add(CITwoPlan.File.Status, Status.find(CITwoPlan.FileStatus.uuid, "Version").getId());
                    update.execute();
                }
            }
        } catch (final JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new Return();
    }

    /**
     * Recursive method to update/insert the task defined by the given
     * workpackages.
     *
     * @param _parameter        Parameter as passed by the eFaps APi
     * @param _projectId        id of tyhe project the tasks belong to
     * @param _wp               workpackage to be used for a task
     * @param _parentTaskId     id of the parent task
     * @throws EFapsException on error
     */
    protected void updateTask(final Parameter _parameter,
                              final Long _projectId,
                              final WorkPackage _wp,
                              final Long _parentTaskId)
        throws EFapsException
    {
        final QueryBuilder queryBldr = new QueryBuilder(CIProjects.TaskAbstract);
        queryBldr.addWhereAttrEqValue(CITwoPlan.TaskAbstract.UUID, _wp.getUuid());
        final InstanceQuery query = queryBldr.getQuery();
        query.execute();
        Update update;
        if (query.next()) {
            update = new Update(query.getCurrentValue());
        } else {
            update = new Insert(CIProjects.TaskScheduled);
        }
        update.add(CIProjects.TaskAbstract.DateFrom, _wp.getStartDate());
        update.add(CIProjects.TaskAbstract.DateUntil, _wp.getFinishDate());
        update.add(CIProjects.TaskAbstract.Name, _wp.getName());
        update.add(CIProjects.TaskAbstract.Description, _wp.getDescription());
        update.add(CIProjects.TaskAbstract.ProjectAbstractLink, _projectId);
        update.add(CIProjects.TaskAbstract.StatusAbstract,
                        Status.find(CIProjects.TaskScheduledStatus.uuid, "Open").getId());
        update.add(CIProjects.TaskAbstract.ParentTaskAbstractLink, _parentTaskId);
        update.add(CITwoPlan.TaskAbstract.UUID, _wp.getUuid());
        update.execute();

        for (final WorkPackage wp : _wp.getChildren()) {
            updateTask(_parameter, _projectId, wp, update.getInstance().getId());
        }
    }

    public Return getFile42plan(final Parameter _parameter)
        throws EFapsException
    {
        final Return ret = new Return();
        File file = null;
        try {
            final FileUtil fileUtil = new FileUtil();
            file = fileUtil.getFile("2plan.prj");
            final FileOutputStream out = new FileOutputStream(file);

            final Instance projectInst = _parameter.getCallInstance();
            final PrintQuery print = new PrintQuery(projectInst);
            print.addAttribute(CIProjects.ProjectAbstract.Name, CIProjects.ProjectAbstract.Date,
                            CIProjects.ProjectAbstract.DueDate);
            print.execute();

            final Project project = new Project();
            project.setContext("manual");
            project.setName(print.<String>getAttribute(CIProjects.ProjectAbstract.Name));
            project.setStartDate(print.<DateTime>getAttribute(CIProjects.ProjectAbstract.Date));
            project.setFinishDate(print.<DateTime>getAttribute(CIProjects.ProjectAbstract.DueDate));

            final Calendar cal = new Calendar();
            cal.setContext("manual");
            cal.setUuid("__E2b0N9eEeGFYMDgrVHNxA");
            project.setCalendar(cal);

            final Maps map2 = new Maps();
            map2.setType("manual");

            final Entries entry2 = new Entries();
            entry2.setKey("net.intime.product.mapentry.standardWorkPackageType");
            entry2.setValue("2");
            map2.getEntries().add(entry2);
            project.getMaps().add(map2);

            project.getWorkPackages().addAll(getWorkPackages(_parameter, projectInst, null));

            final JAXBContext jc = JAXBContext.newInstance(AbstractObject.class, Calendar.class, Entries.class,
                            Maps.class, Project.class, WorkPackage.class, DateTime.class);
            final Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(project, out);
            out.close();
        } catch (final FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final PropertyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ret.put(ReturnValues.VALUES, file);
        return ret;
    }


    protected List<WorkPackage> getWorkPackages(final Parameter _parameter,
                                                final Instance _projectInst,
                                                final Long _parentId)
        throws EFapsException
    {
        final List<WorkPackage> ret = new ArrayList<WorkPackage>();
        final QueryBuilder queryBldr = new QueryBuilder(CITwoPlan.TaskAbstract);
        if (_parentId == null) {
            queryBldr.addWhereAttrIsNull(CITwoPlan.TaskAbstract.ParentTaskAbstractLink);
        } else {
            queryBldr.addWhereAttrEqValue(CITwoPlan.TaskAbstract.ParentTaskAbstractLink, _parentId);
        }
        queryBldr.addWhereAttrEqValue(CITwoPlan.TaskAbstract.ProjectAbstractLink, _projectInst.getId());
        final MultiPrintQuery multi = queryBldr.getPrint();
        multi.addAttribute(CITwoPlan.TaskAbstract.Name, CITwoPlan.TaskAbstract.Description,
                        CITwoPlan.TaskAbstract.DateFrom, CITwoPlan.TaskAbstract.DateUntil,
                        CITwoPlan.TaskAbstract.UUID);
        multi.execute();
        while (multi.next()) {
            final WorkPackage workPackage = new WorkPackage();
            workPackage.setContext("manual");
            workPackage.setType("manual");
            workPackage.setName(multi.<String>getAttribute(CITwoPlan.TaskAbstract.Name));
            workPackage.setDescription(multi.<String>getAttribute(CITwoPlan.TaskAbstract.Description));
            workPackage.setStartDate(multi.<DateTime>getAttribute(CITwoPlan.TaskAbstract.DateFrom));
            workPackage.setFinishDate(multi.<DateTime>getAttribute(CITwoPlan.TaskAbstract.DateUntil));

            // get the uuid of the task, in case that it does not have one,
            // create a new one
            String uuid = multi.<String>getAttribute(CITwoPlan.TaskAbstract.UUID);
            if (uuid == null) {
                uuid = UUID.randomUUID().toString();
                final Update update = new Update(multi.getCurrentInstance());
                update.add(CITwoPlan.TaskAbstract.UUID, uuid);
                update.execute();
            }
            workPackage.setUuid(uuid);
            ret.add(workPackage);
            workPackage.getChildren().addAll(
                            getWorkPackages(_parameter, _projectInst, multi.getCurrentInstance().getId()));
        }
        return ret;
    }
}
