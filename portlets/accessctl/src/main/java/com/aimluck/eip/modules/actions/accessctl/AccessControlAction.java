/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2008 Aimluck,Inc.
 * http://aipostyle.com/
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.aimluck.eip.modules.actions.accessctl;

import org.apache.jetspeed.portal.portlets.VelocityPortlet;
import org.apache.jetspeed.services.logging.JetspeedLogFactoryService;
import org.apache.jetspeed.services.logging.JetspeedLogger;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;

import com.aimluck.eip.accessctl.AccessControlSelectData;
import com.aimluck.eip.modules.actions.common.ALBaseAction;

/**
 * アクセス権のアクションクラスです。 <BR>
 *
 */
public class AccessControlAction extends ALBaseAction {

  /** logger */
  @SuppressWarnings("unused")
  private static final JetspeedLogger logger = JetspeedLogFactoryService
      .getLogger(AccessControlAction.class.getName());

  /**
   * 通常表示の際の処理を記述します。 <BR>
   *
   * @param portlet
   * @param context
   * @param rundata
   * @throws Exception
   * @see org.apache.jetspeed.modules.actions.portlets.VelocityPortletAction#buildNormalContext(org.apache.jetspeed.portal.portlets.VelocityPortlet,
   *      org.apache.velocity.context.Context, org.apache.turbine.util.RunData)
   */
  protected void buildNormalContext(VelocityPortlet portlet, Context context,
      RunData rundata) throws Exception {
    if (getMode() == null) {
      doAccessctl_list(rundata, context);
    }
  }

  /**
   * 最大化表示の際の処理を記述します。 <BR>
   *
   * @param portlet
   * @param context
   * @param rundata
   */
  protected void buildMaximizedContext(VelocityPortlet portlet,
      Context context, RunData rundata) {
  }

  /**
   * アクセス権を一覧表示します。 <BR>
   *
   * @param rundata
   * @param context
   * @throws Exception
   */
  public void doAccessctl_list(RunData rundata, Context context)
      throws Exception {
    AccessControlSelectData listData = new AccessControlSelectData();
    listData.initField();
    listData.loadPortletFeatureList(rundata, context);
    listData.doViewList(this, rundata, context);

    setTemplate(rundata, "accessctl");
  }

}
