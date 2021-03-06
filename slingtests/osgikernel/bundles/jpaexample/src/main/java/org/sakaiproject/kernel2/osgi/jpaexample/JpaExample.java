/*
 * Licensed to the Sakai Foundation (SF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The SF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package org.sakaiproject.kernel2.osgi.jpaexample;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.sakaiproject.kernel2.osgi.jpaexample.jpa.model.ExampleModel;
import org.sakaiproject.kernel2.osgi.jpaprovider.UserManagerFactory;
import org.sakaiproject.kernel2.osgi.jpaprovider.model.SystemUser;

import javax.persistence.EntityManager;

public class JpaExample implements BundleActivator {

  public void start(BundleContext arg0) throws Exception {
    System.err.println("Doing some JPA");
    EntityManager em = UserManagerFactory.getUserManager();
    System.err.println("EM: " + em);

    System.out.println("Creating example model");
    ExampleModel model = new ExampleModel();
    model.setProperty("Some property");
    em.getTransaction().begin();
    em.persist(model);
    em.getTransaction().commit();

    System.out.println("Attempting to read back model from database");
    // model should be written to database now.
    ExampleModel model2 = em.find(ExampleModel.class, model.getId());
    System.out.println("Model " + model.getId() + " from db: " + model2);

    SystemUser u = new SystemUser();
    u.setName("Some user");
    em.getTransaction().begin();
    em.persist(u);
    em.getTransaction().commit();

    // u should be written to database now.
    // Read it from db (no transaction context needed for em.find method)
    SystemUser u2 = em.find(SystemUser.class, u.getId());
    System.out.println("User " + u.getId() + " from db: " + u2);

  }

  public void stop(BundleContext arg0) throws Exception {
  }

}
