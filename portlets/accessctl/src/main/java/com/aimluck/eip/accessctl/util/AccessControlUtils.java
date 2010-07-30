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
package com.aimluck.eip.accessctl.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;
import org.apache.jetspeed.services.logging.JetspeedLogFactoryService;
import org.apache.jetspeed.services.logging.JetspeedLogger;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;

import com.aimluck.commons.field.ALNumberField;
import com.aimluck.eip.accessctl.bean.AccessControlFeatureBean;
import com.aimluck.eip.cayenne.om.account.EipTAclPortletFeature;
import com.aimluck.eip.cayenne.om.account.EipTAclRole;
import com.aimluck.eip.cayenne.om.account.EipTAclUserRoleMap;
import com.aimluck.eip.cayenne.om.security.TurbineUser;
import com.aimluck.eip.common.ALEipConstants;
import com.aimluck.eip.orm.DatabaseOrmService;
import com.aimluck.eip.util.ALEipUtils;

/**
 * ユーティリティクラスです。 <BR>
 *
 */
public class AccessControlUtils {

  /** logger */
  private static final JetspeedLogger logger = JetspeedLogFactoryService
      .getLogger(AccessControlUtils.class.getName());

  /**
   * ロールオブジェクトモデルを取得します。 <BR>
   *
   * @param rundata
   * @param context
   * @return
   */
  public static EipTAclRole getEipTAclRole(RunData rundata, Context context) {
    String aclroleid = ALEipUtils.getTemp(rundata, context,
        ALEipConstants.ENTITY_ID);
    try {
      if (aclroleid == null || Integer.valueOf(aclroleid) == null) {
        // IDが空の場合
        logger.debug("[AccessControlUtils] Empty ID...");
        return null;
      }

      DataContext dataContext = DatabaseOrmService.getInstance()
          .getDataContext();
      Expression exp = ExpressionFactory.matchDbExp(
          EipTAclRole.ROLE_ID_PK_COLUMN, aclroleid);
      SelectQuery query = new SelectQuery(EipTAclRole.class, exp);
      List<?> aclroles = dataContext.performQuery(query);
      if (aclroles == null || aclroles.size() == 0) {
        // 指定したIDのレコードが見つからない場合
        logger.debug("[AccessControlUtils] Not found ID...");
        return null;
      }
      return ((EipTAclRole) aclroles.get(0));
    } catch (Exception ex) {
      logger.error("Exception", ex);
      return null;
    }
  }

  public static List<?> getEipTAclUserRoleMaps(int aclroleid) {
    try {
      DataContext dataContext = DatabaseOrmService.getInstance()
          .getDataContext();
      SelectQuery query = new SelectQuery(EipTAclUserRoleMap.class);

      Expression exp1 = ExpressionFactory.matchDbExp(
          EipTAclUserRoleMap.EIP_TACL_ROLE_PROPERTY + "."
              + EipTAclRole.ROLE_ID_PK_COLUMN, aclroleid);
      query.setQualifier(exp1);

      Expression exp2 = ExpressionFactory.noMatchExp(
          EipTAclUserRoleMap.TURBINE_USER_PROPERTY + "."
              + TurbineUser.DISABLED_PROPERTY, "T");
      query.andQualifier(exp2);

      List<?> aclroles = dataContext.performQuery(query);
      if (aclroles == null || aclroles.size() == 0) {
        // 指定したIDのレコードが見つからない場合
        logger.debug("[AccessControlUtils] Not found ID...");
        return null;
      }
      return aclroles;
    } catch (Exception ex) {
      logger.error("Exception", ex);
      return null;
    }
  }

  public static List<AccessControlFeatureBean> getPortletFeatureList() {
    DataContext dataContext = DatabaseOrmService.getInstance().getDataContext();
    SelectQuery query = new SelectQuery(EipTAclPortletFeature.class);
    List<?> features = dataContext.performQuery(query);
    if (features == null || features.size() == 0) {
      // 指定したIDのレコードが見つからない場合
      logger.debug("[AccessControlUtils] Not found ID...");
      return null;
    }

    List<AccessControlFeatureBean> list = new ArrayList<AccessControlFeatureBean>();

    EipTAclPortletFeature feature = null;
    AccessControlFeatureBean finfo = null;
    int size = features.size();
    for (int i = 0; i < size; i++) {
      feature = (EipTAclPortletFeature) features.get(i);
      finfo = new AccessControlFeatureBean();
      finfo.initField();
      finfo.setFeatureId(feature.getFeatureId().longValue());
      finfo.setFeatureName(feature.getFeatureName());
      finfo.setFeatureAliasName(feature.getFeatureAliasName());
      list.add(finfo);
    }

    return list;
  }

  public static void setupAcl(int defineAclType, int aclType, ALNumberField acl) {
    if ((aclType & defineAclType) == defineAclType) {
      acl.setValue(1);
    }
  }
}
