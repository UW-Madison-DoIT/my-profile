package edu.wisc.my.profile.local.dao;

import javax.sql.DataSource;

import edu.wisc.my.profile.model.ContactInformation;

public class LocalContactInformationDaoImpl extends KeyValueDataStoreDao<ContactInformation> implements LocalContactInformationDao {
  
  public LocalContactInformationDaoImpl(DataSource ds) {
    super(ds);
    super.setPostFix("localContact");
  }

  @Override
  public ContactInformation getData(String key) {
    return super.getData(key, new ContactInformationExtractor());
  }
}
