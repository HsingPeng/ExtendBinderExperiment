// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: app_info.proto

#ifndef PROTOBUF_app_5finfo_2eproto__INCLUDED
#define PROTOBUF_app_5finfo_2eproto__INCLUDED

#include <string>

#include <google/protobuf/stubs/common.h>

#if GOOGLE_PROTOBUF_VERSION < 3005000
#error This file was generated by a newer version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please update
#error your headers.
#endif
#if 3005001 < GOOGLE_PROTOBUF_MIN_PROTOC_VERSION
#error This file was generated by an older version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please
#error regenerate this file with a newer version of protoc.
#endif

#include <google/protobuf/io/coded_stream.h>
#include <google/protobuf/arena.h>
#include <google/protobuf/arenastring.h>
#include <google/protobuf/generated_message_table_driven.h>
#include <google/protobuf/generated_message_util.h>
#include <google/protobuf/metadata.h>
#include <google/protobuf/message.h>
#include <google/protobuf/repeated_field.h>  // IWYU pragma: export
#include <google/protobuf/extension_set.h>  // IWYU pragma: export
#include <google/protobuf/unknown_field_set.h>
// @@protoc_insertion_point(includes)

namespace protobuf_app_5finfo_2eproto {
// Internal implementation detail -- do not use these members.
struct TableStruct {
  static const ::google::protobuf::internal::ParseTableField entries[];
  static const ::google::protobuf::internal::AuxillaryParseTableField aux[];
  static const ::google::protobuf::internal::ParseTable schema[1];
  static const ::google::protobuf::internal::FieldMetadata field_metadata[];
  static const ::google::protobuf::internal::SerializationTable serialization_table[];
  static const ::google::protobuf::uint32 offsets[];
};
void AddDescriptors();
void InitDefaultsInfoImpl();
void InitDefaultsInfo();
inline void InitDefaults() {
  InitDefaultsInfo();
}
}  // namespace protobuf_app_5finfo_2eproto
class Info;
class InfoDefaultTypeInternal;
extern InfoDefaultTypeInternal _Info_default_instance_;

// ===================================================================

class Info : public ::google::protobuf::Message /* @@protoc_insertion_point(class_definition:Info) */ {
 public:
  Info();
  virtual ~Info();

  Info(const Info& from);

  inline Info& operator=(const Info& from) {
    CopyFrom(from);
    return *this;
  }
  #if LANG_CXX11
  Info(Info&& from) noexcept
    : Info() {
    *this = ::std::move(from);
  }

  inline Info& operator=(Info&& from) noexcept {
    if (GetArenaNoVirtual() == from.GetArenaNoVirtual()) {
      if (this != &from) InternalSwap(&from);
    } else {
      CopyFrom(from);
    }
    return *this;
  }
  #endif
  static const ::google::protobuf::Descriptor* descriptor();
  static const Info& default_instance();

  static void InitAsDefaultInstance();  // FOR INTERNAL USE ONLY
  static inline const Info* internal_default_instance() {
    return reinterpret_cast<const Info*>(
               &_Info_default_instance_);
  }
  static PROTOBUF_CONSTEXPR int const kIndexInFileMessages =
    0;

  void Swap(Info* other);
  friend void swap(Info& a, Info& b) {
    a.Swap(&b);
  }

  // implements Message ----------------------------------------------

  inline Info* New() const PROTOBUF_FINAL { return New(NULL); }

  Info* New(::google::protobuf::Arena* arena) const PROTOBUF_FINAL;
  void CopyFrom(const ::google::protobuf::Message& from) PROTOBUF_FINAL;
  void MergeFrom(const ::google::protobuf::Message& from) PROTOBUF_FINAL;
  void CopyFrom(const Info& from);
  void MergeFrom(const Info& from);
  void Clear() PROTOBUF_FINAL;
  bool IsInitialized() const PROTOBUF_FINAL;

  size_t ByteSizeLong() const PROTOBUF_FINAL;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input) PROTOBUF_FINAL;
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const PROTOBUF_FINAL;
  ::google::protobuf::uint8* InternalSerializeWithCachedSizesToArray(
      bool deterministic, ::google::protobuf::uint8* target) const PROTOBUF_FINAL;
  int GetCachedSize() const PROTOBUF_FINAL { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const PROTOBUF_FINAL;
  void InternalSwap(Info* other);
  private:
  inline ::google::protobuf::Arena* GetArenaNoVirtual() const {
    return NULL;
  }
  inline void* MaybeArenaPtr() const {
    return NULL;
  }
  public:

  ::google::protobuf::Metadata GetMetadata() const PROTOBUF_FINAL;

  // nested types ----------------------------------------------------

  // accessors -------------------------------------------------------

  // repeated string activityList = 1;
  int activitylist_size() const;
  void clear_activitylist();
  static const int kActivityListFieldNumber = 1;
  const ::std::string& activitylist(int index) const;
  ::std::string* mutable_activitylist(int index);
  void set_activitylist(int index, const ::std::string& value);
  #if LANG_CXX11
  void set_activitylist(int index, ::std::string&& value);
  #endif
  void set_activitylist(int index, const char* value);
  void set_activitylist(int index, const char* value, size_t size);
  ::std::string* add_activitylist();
  void add_activitylist(const ::std::string& value);
  #if LANG_CXX11
  void add_activitylist(::std::string&& value);
  #endif
  void add_activitylist(const char* value);
  void add_activitylist(const char* value, size_t size);
  const ::google::protobuf::RepeatedPtrField< ::std::string>& activitylist() const;
  ::google::protobuf::RepeatedPtrField< ::std::string>* mutable_activitylist();

  // repeated string serviceList = 2;
  int servicelist_size() const;
  void clear_servicelist();
  static const int kServiceListFieldNumber = 2;
  const ::std::string& servicelist(int index) const;
  ::std::string* mutable_servicelist(int index);
  void set_servicelist(int index, const ::std::string& value);
  #if LANG_CXX11
  void set_servicelist(int index, ::std::string&& value);
  #endif
  void set_servicelist(int index, const char* value);
  void set_servicelist(int index, const char* value, size_t size);
  ::std::string* add_servicelist();
  void add_servicelist(const ::std::string& value);
  #if LANG_CXX11
  void add_servicelist(::std::string&& value);
  #endif
  void add_servicelist(const char* value);
  void add_servicelist(const char* value, size_t size);
  const ::google::protobuf::RepeatedPtrField< ::std::string>& servicelist() const;
  ::google::protobuf::RepeatedPtrField< ::std::string>* mutable_servicelist();

  // repeated string broadcastList = 3;
  int broadcastlist_size() const;
  void clear_broadcastlist();
  static const int kBroadcastListFieldNumber = 3;
  const ::std::string& broadcastlist(int index) const;
  ::std::string* mutable_broadcastlist(int index);
  void set_broadcastlist(int index, const ::std::string& value);
  #if LANG_CXX11
  void set_broadcastlist(int index, ::std::string&& value);
  #endif
  void set_broadcastlist(int index, const char* value);
  void set_broadcastlist(int index, const char* value, size_t size);
  ::std::string* add_broadcastlist();
  void add_broadcastlist(const ::std::string& value);
  #if LANG_CXX11
  void add_broadcastlist(::std::string&& value);
  #endif
  void add_broadcastlist(const char* value);
  void add_broadcastlist(const char* value, size_t size);
  const ::google::protobuf::RepeatedPtrField< ::std::string>& broadcastlist() const;
  ::google::protobuf::RepeatedPtrField< ::std::string>* mutable_broadcastlist();

  // repeated string contentProviderList = 4;
  int contentproviderlist_size() const;
  void clear_contentproviderlist();
  static const int kContentProviderListFieldNumber = 4;
  const ::std::string& contentproviderlist(int index) const;
  ::std::string* mutable_contentproviderlist(int index);
  void set_contentproviderlist(int index, const ::std::string& value);
  #if LANG_CXX11
  void set_contentproviderlist(int index, ::std::string&& value);
  #endif
  void set_contentproviderlist(int index, const char* value);
  void set_contentproviderlist(int index, const char* value, size_t size);
  ::std::string* add_contentproviderlist();
  void add_contentproviderlist(const ::std::string& value);
  #if LANG_CXX11
  void add_contentproviderlist(::std::string&& value);
  #endif
  void add_contentproviderlist(const char* value);
  void add_contentproviderlist(const char* value, size_t size);
  const ::google::protobuf::RepeatedPtrField< ::std::string>& contentproviderlist() const;
  ::google::protobuf::RepeatedPtrField< ::std::string>* mutable_contentproviderlist();

  // @@protoc_insertion_point(class_scope:Info)
 private:

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  ::google::protobuf::RepeatedPtrField< ::std::string> activitylist_;
  ::google::protobuf::RepeatedPtrField< ::std::string> servicelist_;
  ::google::protobuf::RepeatedPtrField< ::std::string> broadcastlist_;
  ::google::protobuf::RepeatedPtrField< ::std::string> contentproviderlist_;
  mutable int _cached_size_;
  friend struct ::protobuf_app_5finfo_2eproto::TableStruct;
  friend void ::protobuf_app_5finfo_2eproto::InitDefaultsInfoImpl();
};
// ===================================================================


// ===================================================================

#ifdef __GNUC__
  #pragma GCC diagnostic push
  #pragma GCC diagnostic ignored "-Wstrict-aliasing"
#endif  // __GNUC__
// Info

// repeated string activityList = 1;
inline int Info::activitylist_size() const {
  return activitylist_.size();
}
inline void Info::clear_activitylist() {
  activitylist_.Clear();
}
inline const ::std::string& Info::activitylist(int index) const {
  // @@protoc_insertion_point(field_get:Info.activityList)
  return activitylist_.Get(index);
}
inline ::std::string* Info::mutable_activitylist(int index) {
  // @@protoc_insertion_point(field_mutable:Info.activityList)
  return activitylist_.Mutable(index);
}
inline void Info::set_activitylist(int index, const ::std::string& value) {
  // @@protoc_insertion_point(field_set:Info.activityList)
  activitylist_.Mutable(index)->assign(value);
}
#if LANG_CXX11
inline void Info::set_activitylist(int index, ::std::string&& value) {
  // @@protoc_insertion_point(field_set:Info.activityList)
  activitylist_.Mutable(index)->assign(std::move(value));
}
#endif
inline void Info::set_activitylist(int index, const char* value) {
  GOOGLE_DCHECK(value != NULL);
  activitylist_.Mutable(index)->assign(value);
  // @@protoc_insertion_point(field_set_char:Info.activityList)
}
inline void Info::set_activitylist(int index, const char* value, size_t size) {
  activitylist_.Mutable(index)->assign(
    reinterpret_cast<const char*>(value), size);
  // @@protoc_insertion_point(field_set_pointer:Info.activityList)
}
inline ::std::string* Info::add_activitylist() {
  // @@protoc_insertion_point(field_add_mutable:Info.activityList)
  return activitylist_.Add();
}
inline void Info::add_activitylist(const ::std::string& value) {
  activitylist_.Add()->assign(value);
  // @@protoc_insertion_point(field_add:Info.activityList)
}
#if LANG_CXX11
inline void Info::add_activitylist(::std::string&& value) {
  activitylist_.Add(std::move(value));
  // @@protoc_insertion_point(field_add:Info.activityList)
}
#endif
inline void Info::add_activitylist(const char* value) {
  GOOGLE_DCHECK(value != NULL);
  activitylist_.Add()->assign(value);
  // @@protoc_insertion_point(field_add_char:Info.activityList)
}
inline void Info::add_activitylist(const char* value, size_t size) {
  activitylist_.Add()->assign(reinterpret_cast<const char*>(value), size);
  // @@protoc_insertion_point(field_add_pointer:Info.activityList)
}
inline const ::google::protobuf::RepeatedPtrField< ::std::string>&
Info::activitylist() const {
  // @@protoc_insertion_point(field_list:Info.activityList)
  return activitylist_;
}
inline ::google::protobuf::RepeatedPtrField< ::std::string>*
Info::mutable_activitylist() {
  // @@protoc_insertion_point(field_mutable_list:Info.activityList)
  return &activitylist_;
}

// repeated string serviceList = 2;
inline int Info::servicelist_size() const {
  return servicelist_.size();
}
inline void Info::clear_servicelist() {
  servicelist_.Clear();
}
inline const ::std::string& Info::servicelist(int index) const {
  // @@protoc_insertion_point(field_get:Info.serviceList)
  return servicelist_.Get(index);
}
inline ::std::string* Info::mutable_servicelist(int index) {
  // @@protoc_insertion_point(field_mutable:Info.serviceList)
  return servicelist_.Mutable(index);
}
inline void Info::set_servicelist(int index, const ::std::string& value) {
  // @@protoc_insertion_point(field_set:Info.serviceList)
  servicelist_.Mutable(index)->assign(value);
}
#if LANG_CXX11
inline void Info::set_servicelist(int index, ::std::string&& value) {
  // @@protoc_insertion_point(field_set:Info.serviceList)
  servicelist_.Mutable(index)->assign(std::move(value));
}
#endif
inline void Info::set_servicelist(int index, const char* value) {
  GOOGLE_DCHECK(value != NULL);
  servicelist_.Mutable(index)->assign(value);
  // @@protoc_insertion_point(field_set_char:Info.serviceList)
}
inline void Info::set_servicelist(int index, const char* value, size_t size) {
  servicelist_.Mutable(index)->assign(
    reinterpret_cast<const char*>(value), size);
  // @@protoc_insertion_point(field_set_pointer:Info.serviceList)
}
inline ::std::string* Info::add_servicelist() {
  // @@protoc_insertion_point(field_add_mutable:Info.serviceList)
  return servicelist_.Add();
}
inline void Info::add_servicelist(const ::std::string& value) {
  servicelist_.Add()->assign(value);
  // @@protoc_insertion_point(field_add:Info.serviceList)
}
#if LANG_CXX11
inline void Info::add_servicelist(::std::string&& value) {
  servicelist_.Add(std::move(value));
  // @@protoc_insertion_point(field_add:Info.serviceList)
}
#endif
inline void Info::add_servicelist(const char* value) {
  GOOGLE_DCHECK(value != NULL);
  servicelist_.Add()->assign(value);
  // @@protoc_insertion_point(field_add_char:Info.serviceList)
}
inline void Info::add_servicelist(const char* value, size_t size) {
  servicelist_.Add()->assign(reinterpret_cast<const char*>(value), size);
  // @@protoc_insertion_point(field_add_pointer:Info.serviceList)
}
inline const ::google::protobuf::RepeatedPtrField< ::std::string>&
Info::servicelist() const {
  // @@protoc_insertion_point(field_list:Info.serviceList)
  return servicelist_;
}
inline ::google::protobuf::RepeatedPtrField< ::std::string>*
Info::mutable_servicelist() {
  // @@protoc_insertion_point(field_mutable_list:Info.serviceList)
  return &servicelist_;
}

// repeated string broadcastList = 3;
inline int Info::broadcastlist_size() const {
  return broadcastlist_.size();
}
inline void Info::clear_broadcastlist() {
  broadcastlist_.Clear();
}
inline const ::std::string& Info::broadcastlist(int index) const {
  // @@protoc_insertion_point(field_get:Info.broadcastList)
  return broadcastlist_.Get(index);
}
inline ::std::string* Info::mutable_broadcastlist(int index) {
  // @@protoc_insertion_point(field_mutable:Info.broadcastList)
  return broadcastlist_.Mutable(index);
}
inline void Info::set_broadcastlist(int index, const ::std::string& value) {
  // @@protoc_insertion_point(field_set:Info.broadcastList)
  broadcastlist_.Mutable(index)->assign(value);
}
#if LANG_CXX11
inline void Info::set_broadcastlist(int index, ::std::string&& value) {
  // @@protoc_insertion_point(field_set:Info.broadcastList)
  broadcastlist_.Mutable(index)->assign(std::move(value));
}
#endif
inline void Info::set_broadcastlist(int index, const char* value) {
  GOOGLE_DCHECK(value != NULL);
  broadcastlist_.Mutable(index)->assign(value);
  // @@protoc_insertion_point(field_set_char:Info.broadcastList)
}
inline void Info::set_broadcastlist(int index, const char* value, size_t size) {
  broadcastlist_.Mutable(index)->assign(
    reinterpret_cast<const char*>(value), size);
  // @@protoc_insertion_point(field_set_pointer:Info.broadcastList)
}
inline ::std::string* Info::add_broadcastlist() {
  // @@protoc_insertion_point(field_add_mutable:Info.broadcastList)
  return broadcastlist_.Add();
}
inline void Info::add_broadcastlist(const ::std::string& value) {
  broadcastlist_.Add()->assign(value);
  // @@protoc_insertion_point(field_add:Info.broadcastList)
}
#if LANG_CXX11
inline void Info::add_broadcastlist(::std::string&& value) {
  broadcastlist_.Add(std::move(value));
  // @@protoc_insertion_point(field_add:Info.broadcastList)
}
#endif
inline void Info::add_broadcastlist(const char* value) {
  GOOGLE_DCHECK(value != NULL);
  broadcastlist_.Add()->assign(value);
  // @@protoc_insertion_point(field_add_char:Info.broadcastList)
}
inline void Info::add_broadcastlist(const char* value, size_t size) {
  broadcastlist_.Add()->assign(reinterpret_cast<const char*>(value), size);
  // @@protoc_insertion_point(field_add_pointer:Info.broadcastList)
}
inline const ::google::protobuf::RepeatedPtrField< ::std::string>&
Info::broadcastlist() const {
  // @@protoc_insertion_point(field_list:Info.broadcastList)
  return broadcastlist_;
}
inline ::google::protobuf::RepeatedPtrField< ::std::string>*
Info::mutable_broadcastlist() {
  // @@protoc_insertion_point(field_mutable_list:Info.broadcastList)
  return &broadcastlist_;
}

// repeated string contentProviderList = 4;
inline int Info::contentproviderlist_size() const {
  return contentproviderlist_.size();
}
inline void Info::clear_contentproviderlist() {
  contentproviderlist_.Clear();
}
inline const ::std::string& Info::contentproviderlist(int index) const {
  // @@protoc_insertion_point(field_get:Info.contentProviderList)
  return contentproviderlist_.Get(index);
}
inline ::std::string* Info::mutable_contentproviderlist(int index) {
  // @@protoc_insertion_point(field_mutable:Info.contentProviderList)
  return contentproviderlist_.Mutable(index);
}
inline void Info::set_contentproviderlist(int index, const ::std::string& value) {
  // @@protoc_insertion_point(field_set:Info.contentProviderList)
  contentproviderlist_.Mutable(index)->assign(value);
}
#if LANG_CXX11
inline void Info::set_contentproviderlist(int index, ::std::string&& value) {
  // @@protoc_insertion_point(field_set:Info.contentProviderList)
  contentproviderlist_.Mutable(index)->assign(std::move(value));
}
#endif
inline void Info::set_contentproviderlist(int index, const char* value) {
  GOOGLE_DCHECK(value != NULL);
  contentproviderlist_.Mutable(index)->assign(value);
  // @@protoc_insertion_point(field_set_char:Info.contentProviderList)
}
inline void Info::set_contentproviderlist(int index, const char* value, size_t size) {
  contentproviderlist_.Mutable(index)->assign(
    reinterpret_cast<const char*>(value), size);
  // @@protoc_insertion_point(field_set_pointer:Info.contentProviderList)
}
inline ::std::string* Info::add_contentproviderlist() {
  // @@protoc_insertion_point(field_add_mutable:Info.contentProviderList)
  return contentproviderlist_.Add();
}
inline void Info::add_contentproviderlist(const ::std::string& value) {
  contentproviderlist_.Add()->assign(value);
  // @@protoc_insertion_point(field_add:Info.contentProviderList)
}
#if LANG_CXX11
inline void Info::add_contentproviderlist(::std::string&& value) {
  contentproviderlist_.Add(std::move(value));
  // @@protoc_insertion_point(field_add:Info.contentProviderList)
}
#endif
inline void Info::add_contentproviderlist(const char* value) {
  GOOGLE_DCHECK(value != NULL);
  contentproviderlist_.Add()->assign(value);
  // @@protoc_insertion_point(field_add_char:Info.contentProviderList)
}
inline void Info::add_contentproviderlist(const char* value, size_t size) {
  contentproviderlist_.Add()->assign(reinterpret_cast<const char*>(value), size);
  // @@protoc_insertion_point(field_add_pointer:Info.contentProviderList)
}
inline const ::google::protobuf::RepeatedPtrField< ::std::string>&
Info::contentproviderlist() const {
  // @@protoc_insertion_point(field_list:Info.contentProviderList)
  return contentproviderlist_;
}
inline ::google::protobuf::RepeatedPtrField< ::std::string>*
Info::mutable_contentproviderlist() {
  // @@protoc_insertion_point(field_mutable_list:Info.contentProviderList)
  return &contentproviderlist_;
}

#ifdef __GNUC__
  #pragma GCC diagnostic pop
#endif  // __GNUC__

// @@protoc_insertion_point(namespace_scope)


// @@protoc_insertion_point(global_scope)

#endif  // PROTOBUF_app_5finfo_2eproto__INCLUDED