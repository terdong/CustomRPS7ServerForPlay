// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!

package com.teamgehem.Protobuf.PacketMessage


import com.trueaccord.scalapb.Descriptors

final case class GehemProtocol(
    packetType: com.teamgehem.Protobuf.PacketMessage.PacketType,
    someString: Option[String] = None,
    someInt32: Option[Int] = None,
    someFloat: Option[Float] = None,
    someInt32List: Seq[Int] = Nil,
    someStringList: Seq[String] = Nil
    ) extends com.trueaccord.scalapb.GeneratedMessage with com.trueaccord.scalapb.Message[GehemProtocol] with com.trueaccord.lenses.Updatable[GehemProtocol] {
    lazy val serializedSize: Int = {
      var __size = 0
      __size += com.google.protobuf.CodedOutputStream.computeEnumSize(1, packetType.id)
      if (someString.isDefined) { __size += com.google.protobuf.CodedOutputStream.computeStringSize(2, someString.get) }
      if (someInt32.isDefined) { __size += com.google.protobuf.CodedOutputStream.computeInt32Size(3, someInt32.get) }
      if (someFloat.isDefined) { __size += com.google.protobuf.CodedOutputStream.computeFloatSize(4, someFloat.get) }
      someInt32List.foreach(someInt32List => __size += com.google.protobuf.CodedOutputStream.computeInt32Size(5, someInt32List))
      someStringList.foreach(someStringList => __size += com.google.protobuf.CodedOutputStream.computeStringSize(6, someStringList))
      __size
    }
    def writeTo(output: com.google.protobuf.CodedOutputStream): Unit = {
      output.writeEnum(1, packetType.id)
      someString.foreach { v => 
        output.writeString(2, v)
      }
      someInt32.foreach { v => 
        output.writeInt32(3, v)
      }
      someFloat.foreach { v => 
        output.writeFloat(4, v)
      }
      someInt32List.foreach { v => 
        output.writeInt32(5, v)
      }
      someStringList.foreach { v => 
        output.writeString(6, v)
      }
    }
    def mergeFrom(__input: com.google.protobuf.CodedInputStream): com.teamgehem.Protobuf.PacketMessage.GehemProtocol = {
      var __packetType = this.packetType
      var __someString = this.someString
      var __someInt32 = this.someInt32
      var __someFloat = this.someFloat
      val __someInt32List = (scala.collection.immutable.Vector.newBuilder[Int] ++= this.someInt32List)
      val __someStringList = (scala.collection.immutable.Vector.newBuilder[String] ++= this.someStringList)
      var _done__ = false
      while (!_done__) {
        val _tag__ = __input.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 8 =>
            __packetType = com.teamgehem.Protobuf.PacketMessage.PacketType.fromValue(__input.readEnum())
          case 18 =>
            __someString = Some(__input.readString())
          case 24 =>
            __someInt32 = Some(__input.readInt32())
          case 37 =>
            __someFloat = Some(__input.readFloat())
          case 40 =>
            __someInt32List += __input.readInt32()
          case 50 =>
            __someStringList += __input.readString()
          case tag => __input.skipField(tag)
        }
      }
      com.teamgehem.Protobuf.PacketMessage.GehemProtocol(
          packetType = __packetType,
          someString = __someString,
          someInt32 = __someInt32,
          someFloat = __someFloat,
          someInt32List = __someInt32List.result(),
          someStringList = __someStringList.result()
      )
    }
    def withPacketType(__v: com.teamgehem.Protobuf.PacketMessage.PacketType): GehemProtocol = copy(packetType = __v)
    def getSomeString: String = someString.getOrElse("")
    def clearSomeString: GehemProtocol = copy(someString = None)
    def withSomeString(__v: String): GehemProtocol = copy(someString = Some(__v))
    def getSomeInt32: Int = someInt32.getOrElse(0)
    def clearSomeInt32: GehemProtocol = copy(someInt32 = None)
    def withSomeInt32(__v: Int): GehemProtocol = copy(someInt32 = Some(__v))
    def getSomeFloat: Float = someFloat.getOrElse(0.0f)
    def clearSomeFloat: GehemProtocol = copy(someFloat = None)
    def withSomeFloat(__v: Float): GehemProtocol = copy(someFloat = Some(__v))
    def clearSomeInt32List = copy(someInt32List = Nil)
    def addSomeInt32List(__vs: Int*): GehemProtocol = addAllSomeInt32List(__vs)
    def addAllSomeInt32List(__vs: TraversableOnce[Int]): GehemProtocol = copy(someInt32List = someInt32List ++ __vs)
    def withSomeInt32List(__v: Seq[Int]): GehemProtocol = copy(someInt32List = __v)
    def clearSomeStringList = copy(someStringList = Nil)
    def addSomeStringList(__vs: String*): GehemProtocol = addAllSomeStringList(__vs)
    def addAllSomeStringList(__vs: TraversableOnce[String]): GehemProtocol = copy(someStringList = someStringList ++ __vs)
    def withSomeStringList(__v: Seq[String]): GehemProtocol = copy(someStringList = __v)
    def getField(__field: Descriptors.FieldDescriptor): Any = {
      __field.number match {
        case 1 => packetType
        case 2 => someString
        case 3 => someInt32
        case 4 => someFloat
        case 5 => someInt32List
        case 6 => someStringList
      }
    }
    def companion = com.teamgehem.Protobuf.PacketMessage.GehemProtocol
}

object GehemProtocol extends com.trueaccord.scalapb.GeneratedMessageCompanion[GehemProtocol]  {
  implicit def messageCompanion: com.trueaccord.scalapb.GeneratedMessageCompanion[GehemProtocol]  = this
  def fromFieldsMap(fieldsMap: Map[Int, Any]): com.teamgehem.Protobuf.PacketMessage.GehemProtocol = com.teamgehem.Protobuf.PacketMessage.GehemProtocol(
    packetType = fieldsMap(1).asInstanceOf[com.teamgehem.Protobuf.PacketMessage.PacketType],
    someString = fieldsMap.getOrElse(2, None).asInstanceOf[Option[String]],
    someInt32 = fieldsMap.getOrElse(3, None).asInstanceOf[Option[Int]],
    someFloat = fieldsMap.getOrElse(4, None).asInstanceOf[Option[Float]],
    someInt32List = fieldsMap.getOrElse(5, Nil).asInstanceOf[Seq[Int]],
    someStringList = fieldsMap.getOrElse(6, Nil).asInstanceOf[Seq[String]]
  )
  lazy val descriptor = new Descriptors.MessageDescriptor("GehemProtocol", this,
    None, m = Seq(),
    e = Seq(),
    f = com.teamgehem.Protobuf.PacketMessage.InternalFields_packetMessageProto.internalFieldsFor("com.teamgehem.Protobuf.PacketMessage.GehemProtocol"))
  lazy val defaultInstance = com.teamgehem.Protobuf.PacketMessage.GehemProtocol(
    packetType = com.teamgehem.Protobuf.PacketMessage.PacketType.NONE
  )
  implicit class GehemProtocolLens[UpperPB](_l: com.trueaccord.lenses.Lens[UpperPB, GehemProtocol]) extends com.trueaccord.lenses.ObjectLens[UpperPB, GehemProtocol](_l) {
    def packetType: com.trueaccord.lenses.Lens[UpperPB, com.teamgehem.Protobuf.PacketMessage.PacketType] = field(_.packetType)((c_, f_) => c_.copy(packetType = f_))
    def someString: com.trueaccord.lenses.Lens[UpperPB, String] = field(_.getSomeString)((c_, f_) => c_.copy(someString = Some(f_)))
    def optionalSomeString: com.trueaccord.lenses.Lens[UpperPB, Option[String]] = field(_.someString)((c_, f_) => c_.copy(someString = f_))
    def someInt32: com.trueaccord.lenses.Lens[UpperPB, Int] = field(_.getSomeInt32)((c_, f_) => c_.copy(someInt32 = Some(f_)))
    def optionalSomeInt32: com.trueaccord.lenses.Lens[UpperPB, Option[Int]] = field(_.someInt32)((c_, f_) => c_.copy(someInt32 = f_))
    def someFloat: com.trueaccord.lenses.Lens[UpperPB, Float] = field(_.getSomeFloat)((c_, f_) => c_.copy(someFloat = Some(f_)))
    def optionalSomeFloat: com.trueaccord.lenses.Lens[UpperPB, Option[Float]] = field(_.someFloat)((c_, f_) => c_.copy(someFloat = f_))
    def someInt32List: com.trueaccord.lenses.Lens[UpperPB, Seq[Int]] = field(_.someInt32List)((c_, f_) => c_.copy(someInt32List = f_))
    def someStringList: com.trueaccord.lenses.Lens[UpperPB, Seq[String]] = field(_.someStringList)((c_, f_) => c_.copy(someStringList = f_))
  }
  final val PACKETTYPE_FIELD_NUMBER = 1
  final val SOMESTRING_FIELD_NUMBER = 2
  final val SOMEINT32_FIELD_NUMBER = 3
  final val SOMEFLOAT_FIELD_NUMBER = 4
  final val SOMEINT32LIST_FIELD_NUMBER = 5
  final val SOMESTRINGLIST_FIELD_NUMBER = 6
}
