package com.mydao.nsop.client.vo;

/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.11.0)", date = "2018-06-04")
public class VehInfo implements org.apache.thrift.TBase<VehInfo, VehInfo._Fields>, java.io.Serializable, Cloneable, Comparable<VehInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("VehInfo");

  private static final org.apache.thrift.protocol.TField PLATENO_FIELD_DESC = new org.apache.thrift.protocol.TField("plateno", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField PLATECOLOR_FIELD_DESC = new org.apache.thrift.protocol.TField("platecolor", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField WHITE_FLAG_FIELD_DESC = new org.apache.thrift.protocol.TField("whiteFlag", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField VEH_CLASS_FIELD_DESC = new org.apache.thrift.protocol.TField("vehClass", org.apache.thrift.protocol.TType.STRING, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new VehInfoStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new VehInfoTupleSchemeFactory();

  public String plateno; // required
  public int platecolor; // required
  public int whiteFlag; // required
  public String vehClass; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PLATENO((short)1, "plateno"),
    PLATECOLOR((short)2, "platecolor"),
    WHITE_FLAG((short)3, "whiteFlag"),
    VEH_CLASS((short)4, "vehClass");

    private static final java.util.Map<String, _Fields> byName = new java.util.HashMap<String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // PLATENO
          return PLATENO;
        case 2: // PLATECOLOR
          return PLATECOLOR;
        case 3: // WHITE_FLAG
          return WHITE_FLAG;
        case 4: // VEH_CLASS
          return VEH_CLASS;
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
  private static final int __PLATECOLOR_ISSET_ID = 0;
  private static final int __WHITEFLAG_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PLATENO, new org.apache.thrift.meta_data.FieldMetaData("plateno", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PLATECOLOR, new org.apache.thrift.meta_data.FieldMetaData("platecolor", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.WHITE_FLAG, new org.apache.thrift.meta_data.FieldMetaData("whiteFlag", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.VEH_CLASS, new org.apache.thrift.meta_data.FieldMetaData("vehClass", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(VehInfo.class, metaDataMap);
  }

  public VehInfo() {
  }

  public VehInfo(
    String plateno,
    int platecolor,
    int whiteFlag,
    String vehClass)
  {
    this();
    this.plateno = plateno;
    this.platecolor = platecolor;
    setPlatecolorIsSet(true);
    this.whiteFlag = whiteFlag;
    setWhiteFlagIsSet(true);
    this.vehClass = vehClass;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public VehInfo(VehInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetPlateno()) {
      this.plateno = other.plateno;
    }
    this.platecolor = other.platecolor;
    this.whiteFlag = other.whiteFlag;
    if (other.isSetVehClass()) {
      this.vehClass = other.vehClass;
    }
  }

  public VehInfo deepCopy() {
    return new VehInfo(this);
  }

  @Override
  public void clear() {
    this.plateno = null;
    setPlatecolorIsSet(false);
    this.platecolor = 0;
    setWhiteFlagIsSet(false);
    this.whiteFlag = 0;
    this.vehClass = null;
  }

  public String getPlateno() {
    return this.plateno;
  }

  public VehInfo setPlateno(String plateno) {
    this.plateno = plateno;
    return this;
  }

  public void unsetPlateno() {
    this.plateno = null;
  }

  /** Returns true if field plateno is set (has been assigned a value) and false otherwise */
  public boolean isSetPlateno() {
    return this.plateno != null;
  }

  public void setPlatenoIsSet(boolean value) {
    if (!value) {
      this.plateno = null;
    }
  }

  public int getPlatecolor() {
    return this.platecolor;
  }

  public VehInfo setPlatecolor(int platecolor) {
    this.platecolor = platecolor;
    setPlatecolorIsSet(true);
    return this;
  }

  public void unsetPlatecolor() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __PLATECOLOR_ISSET_ID);
  }

  /** Returns true if field platecolor is set (has been assigned a value) and false otherwise */
  public boolean isSetPlatecolor() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __PLATECOLOR_ISSET_ID);
  }

  public void setPlatecolorIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __PLATECOLOR_ISSET_ID, value);
  }

  public int getWhiteFlag() {
    return this.whiteFlag;
  }

  public VehInfo setWhiteFlag(int whiteFlag) {
    this.whiteFlag = whiteFlag;
    setWhiteFlagIsSet(true);
    return this;
  }

  public void unsetWhiteFlag() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __WHITEFLAG_ISSET_ID);
  }

  /** Returns true if field whiteFlag is set (has been assigned a value) and false otherwise */
  public boolean isSetWhiteFlag() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __WHITEFLAG_ISSET_ID);
  }

  public void setWhiteFlagIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __WHITEFLAG_ISSET_ID, value);
  }

  public String getVehClass() {
    return this.vehClass;
  }

  public VehInfo setVehClass(String vehClass) {
    this.vehClass = vehClass;
    return this;
  }

  public void unsetVehClass() {
    this.vehClass = null;
  }

  /** Returns true if field vehClass is set (has been assigned a value) and false otherwise */
  public boolean isSetVehClass() {
    return this.vehClass != null;
  }

  public void setVehClassIsSet(boolean value) {
    if (!value) {
      this.vehClass = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PLATENO:
      if (value == null) {
        unsetPlateno();
      } else {
        setPlateno((String)value);
      }
      break;

    case PLATECOLOR:
      if (value == null) {
        unsetPlatecolor();
      } else {
        setPlatecolor((Integer)value);
      }
      break;

    case WHITE_FLAG:
      if (value == null) {
        unsetWhiteFlag();
      } else {
        setWhiteFlag((Integer)value);
      }
      break;

    case VEH_CLASS:
      if (value == null) {
        unsetVehClass();
      } else {
        setVehClass((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PLATENO:
      return getPlateno();

    case PLATECOLOR:
      return getPlatecolor();

    case WHITE_FLAG:
      return getWhiteFlag();

    case VEH_CLASS:
      return getVehClass();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PLATENO:
      return isSetPlateno();
    case PLATECOLOR:
      return isSetPlatecolor();
    case WHITE_FLAG:
      return isSetWhiteFlag();
    case VEH_CLASS:
      return isSetVehClass();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof VehInfo)
      return this.equals((VehInfo)that);
    return false;
  }

  public boolean equals(VehInfo that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_plateno = true && this.isSetPlateno();
    boolean that_present_plateno = true && that.isSetPlateno();
    if (this_present_plateno || that_present_plateno) {
      if (!(this_present_plateno && that_present_plateno))
        return false;
      if (!this.plateno.equals(that.plateno))
        return false;
    }

    boolean this_present_platecolor = true;
    boolean that_present_platecolor = true;
    if (this_present_platecolor || that_present_platecolor) {
      if (!(this_present_platecolor && that_present_platecolor))
        return false;
      if (this.platecolor != that.platecolor)
        return false;
    }

    boolean this_present_whiteFlag = true;
    boolean that_present_whiteFlag = true;
    if (this_present_whiteFlag || that_present_whiteFlag) {
      if (!(this_present_whiteFlag && that_present_whiteFlag))
        return false;
      if (this.whiteFlag != that.whiteFlag)
        return false;
    }

    boolean this_present_vehClass = true && this.isSetVehClass();
    boolean that_present_vehClass = true && that.isSetVehClass();
    if (this_present_vehClass || that_present_vehClass) {
      if (!(this_present_vehClass && that_present_vehClass))
        return false;
      if (!this.vehClass.equals(that.vehClass))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetPlateno()) ? 131071 : 524287);
    if (isSetPlateno())
      hashCode = hashCode * 8191 + plateno.hashCode();

    hashCode = hashCode * 8191 + platecolor;

    hashCode = hashCode * 8191 + whiteFlag;

    hashCode = hashCode * 8191 + ((isSetVehClass()) ? 131071 : 524287);
    if (isSetVehClass())
      hashCode = hashCode * 8191 + vehClass.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(VehInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetPlateno()).compareTo(other.isSetPlateno());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPlateno()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.plateno, other.plateno);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPlatecolor()).compareTo(other.isSetPlatecolor());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPlatecolor()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.platecolor, other.platecolor);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetWhiteFlag()).compareTo(other.isSetWhiteFlag());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetWhiteFlag()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.whiteFlag, other.whiteFlag);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetVehClass()).compareTo(other.isSetVehClass());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVehClass()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.vehClass, other.vehClass);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("VehInfo(");
    boolean first = true;

    sb.append("plateno:");
    if (this.plateno == null) {
      sb.append("null");
    } else {
      sb.append(this.plateno);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("platecolor:");
    sb.append(this.platecolor);
    first = false;
    if (!first) sb.append(", ");
    sb.append("whiteFlag:");
    sb.append(this.whiteFlag);
    first = false;
    if (!first) sb.append(", ");
    sb.append("vehClass:");
    if (this.vehClass == null) {
      sb.append("null");
    } else {
      sb.append(this.vehClass);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class VehInfoStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public VehInfoStandardScheme getScheme() {
      return new VehInfoStandardScheme();
    }
  }

  private static class VehInfoStandardScheme extends org.apache.thrift.scheme.StandardScheme<VehInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, VehInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PLATENO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.plateno = iprot.readString();
              struct.setPlatenoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PLATECOLOR
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.platecolor = iprot.readI32();
              struct.setPlatecolorIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // WHITE_FLAG
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.whiteFlag = iprot.readI32();
              struct.setWhiteFlagIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // VEH_CLASS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.vehClass = iprot.readString();
              struct.setVehClassIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, VehInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.plateno != null) {
        oprot.writeFieldBegin(PLATENO_FIELD_DESC);
        oprot.writeString(struct.plateno);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(PLATECOLOR_FIELD_DESC);
      oprot.writeI32(struct.platecolor);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(WHITE_FLAG_FIELD_DESC);
      oprot.writeI32(struct.whiteFlag);
      oprot.writeFieldEnd();
      if (struct.vehClass != null) {
        oprot.writeFieldBegin(VEH_CLASS_FIELD_DESC);
        oprot.writeString(struct.vehClass);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class VehInfoTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public VehInfoTupleScheme getScheme() {
      return new VehInfoTupleScheme();
    }
  }

  private static class VehInfoTupleScheme extends org.apache.thrift.scheme.TupleScheme<VehInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, VehInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetPlateno()) {
        optionals.set(0);
      }
      if (struct.isSetPlatecolor()) {
        optionals.set(1);
      }
      if (struct.isSetWhiteFlag()) {
        optionals.set(2);
      }
      if (struct.isSetVehClass()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetPlateno()) {
        oprot.writeString(struct.plateno);
      }
      if (struct.isSetPlatecolor()) {
        oprot.writeI32(struct.platecolor);
      }
      if (struct.isSetWhiteFlag()) {
        oprot.writeI32(struct.whiteFlag);
      }
      if (struct.isSetVehClass()) {
        oprot.writeString(struct.vehClass);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, VehInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.plateno = iprot.readString();
        struct.setPlatenoIsSet(true);
      }
      if (incoming.get(1)) {
        struct.platecolor = iprot.readI32();
        struct.setPlatecolorIsSet(true);
      }
      if (incoming.get(2)) {
        struct.whiteFlag = iprot.readI32();
        struct.setWhiteFlagIsSet(true);
      }
      if (incoming.get(3)) {
        struct.vehClass = iprot.readString();
        struct.setVehClassIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

