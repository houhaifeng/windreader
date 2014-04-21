/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package cn.wind.com.reader.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.thrift.*;
import org.apache.thrift.async.*;
import org.apache.thrift.meta_data.*;
import org.apache.thrift.transport.*;
import org.apache.thrift.protocol.*;

public class ListParam implements TBase<ListParam, ListParam._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("ListParam");

  private static final TField OFFSET_FIELD_DESC = new TField("offset", TType.I32, (short)1);
  private static final TField LENGTH_FIELD_DESC = new TField("length", TType.I32, (short)2);
  private static final TField SORT_FIELD_DESC = new TField("sort", TType.I32, (short)3);

  private int offset;
  private int length;
  private int sort;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    OFFSET((short)1, "offset"),
    LENGTH((short)2, "length"),
    SORT((short)3, "sort");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // OFFSET
          return OFFSET;
        case 2: // LENGTH
          return LENGTH;
        case 3: // SORT
          return SORT;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __OFFSET_ISSET_ID = 0;
  private static final int __LENGTH_ISSET_ID = 1;
  private static final int __SORT_ISSET_ID = 2;
  private BitSet __isset_bit_vector = new BitSet(3);

  public static final Map<_Fields, FieldMetaData> metaDataMap;
  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.OFFSET, new FieldMetaData("offset", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.LENGTH, new FieldMetaData("length", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.SORT, new FieldMetaData("sort", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(ListParam.class, metaDataMap);
  }

  public ListParam() {
  }

  public ListParam(
    int offset,
    int length,
    int sort)
  {
    this();
    this.offset = offset;
    setOffsetIsSet(true);
    this.length = length;
    setLengthIsSet(true);
    this.sort = sort;
    setSortIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ListParam(ListParam other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    this.offset = other.offset;
    this.length = other.length;
    this.sort = other.sort;
  }

  public ListParam deepCopy() {
    return new ListParam(this);
  }

  public void clear() {
    setOffsetIsSet(false);
    this.offset = 0;
    setLengthIsSet(false);
    this.length = 0;
    setSortIsSet(false);
    this.sort = 0;
  }

  public int getOffset() {
    return this.offset;
  }

  public void setOffset(int offset) {
    this.offset = offset;
    setOffsetIsSet(true);
  }

  public void unsetOffset() {
    __isset_bit_vector.clear(__OFFSET_ISSET_ID);
  }

  /** Returns true if field offset is set (has been asigned a value) and false otherwise */
  public boolean isSetOffset() {
    return __isset_bit_vector.get(__OFFSET_ISSET_ID);
  }

  public void setOffsetIsSet(boolean value) {
    __isset_bit_vector.set(__OFFSET_ISSET_ID, value);
  }

  public int getLength() {
    return this.length;
  }

  public void setLength(int length) {
    this.length = length;
    setLengthIsSet(true);
  }

  public void unsetLength() {
    __isset_bit_vector.clear(__LENGTH_ISSET_ID);
  }

  /** Returns true if field length is set (has been asigned a value) and false otherwise */
  public boolean isSetLength() {
    return __isset_bit_vector.get(__LENGTH_ISSET_ID);
  }

  public void setLengthIsSet(boolean value) {
    __isset_bit_vector.set(__LENGTH_ISSET_ID, value);
  }

  public int getSort() {
    return this.sort;
  }

  public void setSort(int sort) {
    this.sort = sort;
    setSortIsSet(true);
  }

  public void unsetSort() {
    __isset_bit_vector.clear(__SORT_ISSET_ID);
  }

  /** Returns true if field sort is set (has been asigned a value) and false otherwise */
  public boolean isSetSort() {
    return __isset_bit_vector.get(__SORT_ISSET_ID);
  }

  public void setSortIsSet(boolean value) {
    __isset_bit_vector.set(__SORT_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case OFFSET:
      if (value == null) {
        unsetOffset();
      } else {
        setOffset((Integer)value);
      }
      break;

    case LENGTH:
      if (value == null) {
        unsetLength();
      } else {
        setLength((Integer)value);
      }
      break;

    case SORT:
      if (value == null) {
        unsetSort();
      } else {
        setSort((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case OFFSET:
      return new Integer(getOffset());

    case LENGTH:
      return new Integer(getLength());

    case SORT:
      return new Integer(getSort());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case OFFSET:
      return isSetOffset();
    case LENGTH:
      return isSetLength();
    case SORT:
      return isSetSort();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ListParam)
      return this.equals((ListParam)that);
    return false;
  }

  public boolean equals(ListParam that) {
    if (that == null)
      return false;

    boolean this_present_offset = true;
    boolean that_present_offset = true;
    if (this_present_offset || that_present_offset) {
      if (!(this_present_offset && that_present_offset))
        return false;
      if (this.offset != that.offset)
        return false;
    }

    boolean this_present_length = true;
    boolean that_present_length = true;
    if (this_present_length || that_present_length) {
      if (!(this_present_length && that_present_length))
        return false;
      if (this.length != that.length)
        return false;
    }

    boolean this_present_sort = true;
    boolean that_present_sort = true;
    if (this_present_sort || that_present_sort) {
      if (!(this_present_sort && that_present_sort))
        return false;
      if (this.sort != that.sort)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(ListParam other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    ListParam typedOther = (ListParam)other;

    lastComparison = Boolean.valueOf(isSetOffset()).compareTo(typedOther.isSetOffset());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOffset()) {
      lastComparison = TBaseHelper.compareTo(this.offset, typedOther.offset);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLength()).compareTo(typedOther.isSetLength());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLength()) {
      lastComparison = TBaseHelper.compareTo(this.length, typedOther.length);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSort()).compareTo(typedOther.isSetSort());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSort()) {
      lastComparison = TBaseHelper.compareTo(this.sort, typedOther.sort);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(TProtocol iprot) throws TException {
    TField field;
    iprot.readStructBegin();
    while (true)
    {
      field = iprot.readFieldBegin();
      if (field.type == TType.STOP) { 
        break;
      }
      switch (field.id) {
        case 1: // OFFSET
          if (field.type == TType.I32) {
            this.offset = iprot.readI32();
            setOffsetIsSet(true);
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // LENGTH
          if (field.type == TType.I32) {
            this.length = iprot.readI32();
            setLengthIsSet(true);
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // SORT
          if (field.type == TType.I32) {
            this.sort = iprot.readI32();
            setSortIsSet(true);
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        default:
          TProtocolUtil.skip(iprot, field.type);
      }
      iprot.readFieldEnd();
    }
    iprot.readStructEnd();
    validate();
  }

  public void write(TProtocol oprot) throws TException {
    validate();

    oprot.writeStructBegin(STRUCT_DESC);
    oprot.writeFieldBegin(OFFSET_FIELD_DESC);
    oprot.writeI32(this.offset);
    oprot.writeFieldEnd();
    oprot.writeFieldBegin(LENGTH_FIELD_DESC);
    oprot.writeI32(this.length);
    oprot.writeFieldEnd();
    oprot.writeFieldBegin(SORT_FIELD_DESC);
    oprot.writeI32(this.sort);
    oprot.writeFieldEnd();
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ListParam(");
    boolean first = true;

    sb.append("offset:");
    sb.append(this.offset);
    first = false;
    if (!first) sb.append(", ");
    sb.append("length:");
    sb.append(this.length);
    first = false;
    if (!first) sb.append(", ");
    sb.append("sort:");
    sb.append(this.sort);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }

}

