// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!

package com.teamgehem.Protobuf.PacketMessage


import com.trueaccord.scalapb.Descriptors

object InternalFields_packetMessageProto {
  def internalFieldsFor(scalaName: String): Seq[Descriptors.FieldDescriptor] = scalaName match {
    case "com.teamgehem.Protobuf.PacketMessage.GehemProtocol" => Seq(Descriptors.FieldDescriptor(0, 1, "packetType", Descriptors.Required, Descriptors.EnumType(com.teamgehem.Protobuf.PacketMessage.PacketType.descriptor), isPacked = false, containingOneofName = None), Descriptors.FieldDescriptor(1, 2, "testList", Descriptors.Repeated, Descriptors.PrimitiveType(com.google.protobuf.Descriptors.FieldDescriptor.JavaType.INT, com.google.protobuf.Descriptors.FieldDescriptor.Type.INT32), isPacked = false, containingOneofName = None))
  }
}