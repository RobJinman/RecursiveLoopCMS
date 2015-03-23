package com.recursiveloop.cms.jcrbeans;

import org.apache.jackrabbit.ocm.mapper.impl.annotation.Field;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.Collection;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.Node;
import java.util.List;
import java.util.ArrayList;


@Node
public class Definition {
  private String m_path;
  private String m_name = null;

  // @Collection must go on data member, not getter method (for some reason)
  @Collection()
  public List<FieldDef> fields;

  public Definition() {}

  public Definition(String name) {
    m_name = name;
  }

  public Definition(String name, List<FieldDef> fields) {
    m_name = name;
    this.fields = fields;
  }

  public Definition(Definition cpy) {
    m_path = cpy.m_path;
    m_name = cpy.m_name;

    this.fields = new ArrayList<FieldDef>();
    for (FieldDef f : cpy.fields) {
      this.fields.add(new FieldDef(f));
    }
  }

  @Field(jcrName = "path", path = true)
  public String getPath() {
    return m_path;
  }

  public void setPath(String path) {
    m_path = path;
  }

  @Field(jcrName = "name")
  public String getName() {
    return m_name;
  }

  public void setName(String name) {
    m_name = name;
  }

  public List<FieldDef> getFields() {
    return this.fields;
  }

  public void setFields(List<FieldDef> fields) {
    this.fields = new ArrayList<FieldDef>();

    for (FieldDef f : fields) {
      this.fields.add(new FieldDef(f));
    }
  }

  public void addField(FieldDef field) {
    if (this.fields == null) {
      this.fields = new ArrayList<FieldDef>();
    }

    this.fields.add(field);
  }
}
