// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: intent.proto

#ifndef PROTOBUF_intent_2eproto__INCLUDED
#define PROTOBUF_intent_2eproto__INCLUDED

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
#include <google/protobuf/map.h>  // IWYU pragma: export
#include <google/protobuf/map_entry.h>
#include <google/protobuf/map_field_inl.h>
#include <google/protobuf/generated_enum_reflection.h>
#include <google/protobuf/unknown_field_set.h>
// @@protoc_insertion_point(includes)

namespace protobuf_intent_2eproto {
// Internal implementation detail -- do not use these members.
struct TableStruct {
  static const ::google::protobuf::internal::ParseTableField entries[];
  static const ::google::protobuf::internal::AuxillaryParseTableField aux[];
  static const ::google::protobuf::internal::ParseTable schema[2];
  static const ::google::protobuf::internal::FieldMetadata field_metadata[];
  static const ::google::protobuf::internal::SerializationTable serialization_table[];
  static const ::google::protobuf::uint32 offsets[];
};
void AddDescriptors();
void InitDefaultsRequest_ExtrasEntry_DoNotUseImpl();
void InitDefaultsRequest_ExtrasEntry_DoNotUse();
void InitDefaultsRequestImpl();
void InitDefaultsRequest();
inline void InitDefaults() {
  InitDefaultsRequest_ExtrasEntry_DoNotUse();
  InitDefaultsRequest();
}
}  // namespace protobuf_intent_2eproto
class Request;
class RequestDefaultTypeInternal;
extern RequestDefaultTypeInternal _Request_default_instance_;
class Request_ExtrasEntry_DoNotUse;
class Request_ExtrasEntry_DoNotUseDefaultTypeInternal;
extern Request_ExtrasEntry_DoNotUseDefaultTypeInternal _Request_ExtrasEntry_DoNotUse_default_instance_;

enum Request_Method {
  Request_Method_startActivity = 0,
  Request_Method_startService = 1,
  Request_Method_sendBroadcast = 2,
  Request_Method_Request_Method_INT_MIN_SENTINEL_DO_NOT_USE_ = ::google::protobuf::kint32min,
  Request_Method_Request_Method_INT_MAX_SENTINEL_DO_NOT_USE_ = ::google::protobuf::kint32max
};
bool Request_Method_IsValid(int value);
const Request_Method Request_Method_Method_MIN = Request_Method_startActivity;
const Request_Method Request_Method_Method_MAX = Request_Method_sendBroadcast;
const int Request_Method_Method_ARRAYSIZE = Request_Method_Method_MAX + 1;

const ::google::protobuf::EnumDescriptor* Request_Method_descriptor();
inline const ::std::string& Request_Method_Name(Request_Method value) {
  return ::google::protobuf::internal::NameOfEnum(
    Request_Method_descriptor(), value);
}
inline bool Request_Method_Parse(
    const ::std::string& name, Request_Method* value) {
  return ::google::protobuf::internal::ParseNamedEnum<Request_Method>(
    Request_Method_descriptor(), name, value);
}
// ===================================================================

class Request_ExtrasEntry_DoNotUse : public ::google::protobuf::internal::MapEntry<Request_ExtrasEntry_DoNotUse, 
    ::std::string, ::std::string,
    ::google::protobuf::internal::WireFormatLite::TYPE_STRING,
    ::google::protobuf::internal::WireFormatLite::TYPE_STRING,
    0 > {
public:
  typedef ::google::protobuf::internal::MapEntry<Request_ExtrasEntry_DoNotUse, 
    ::std::string, ::std::string,
    ::google::protobuf::internal::WireFormatLite::TYPE_STRING,
    ::google::protobuf::internal::WireFormatLite::TYPE_STRING,
    0 > SuperType;
  Request_ExtrasEntry_DoNotUse();
  Request_ExtrasEntry_DoNotUse(::google::protobuf::Arena* arena);
  void MergeFrom(const Request_ExtrasEntry_DoNotUse& other);
  static const Request_ExtrasEntry_DoNotUse* internal_default_instance() { return reinterpret_cast<const Request_ExtrasEntry_DoNotUse*>(&_Request_ExtrasEntry_DoNotUse_default_instance_); }
  void MergeFrom(const ::google::protobuf::Message& other) PROTOBUF_FINAL;
  ::google::protobuf::Metadata GetMetadata() const;
};

// -------------------------------------------------------------------

class Request : public ::google::protobuf::Message /* @@protoc_insertion_point(class_definition:Request) */ {
 public:
  Request();
  virtual ~Request();

  Request(const Request& from);

  inline Request& operator=(const Request& from) {
    CopyFrom(from);
    return *this;
  }
  #if LANG_CXX11
  Request(Request&& from) noexcept
    : Request() {
    *this = ::std::move(from);
  }

  inline Request& operator=(Request&& from) noexcept {
    if (GetArenaNoVirtual() == from.GetArenaNoVirtual()) {
      if (this != &from) InternalSwap(&from);
    } else {
      CopyFrom(from);
    }
    return *this;
  }
  #endif
  static const ::google::protobuf::Descriptor* descriptor();
  static const Request& default_instance();

  static void InitAsDefaultInstance();  // FOR INTERNAL USE ONLY
  static inline const Request* internal_default_instance() {
    return reinterpret_cast<const Request*>(
               &_Request_default_instance_);
  }
  static PROTOBUF_CONSTEXPR int const kIndexInFileMessages =
    1;

  void Swap(Request* other);
  friend void swap(Request& a, Request& b) {
    a.Swap(&b);
  }

  // implements Message ----------------------------------------------

  inline Request* New() const PROTOBUF_FINAL { return New(NULL); }

  Request* New(::google::protobuf::Arena* arena) const PROTOBUF_FINAL;
  void CopyFrom(const ::google::protobuf::Message& from) PROTOBUF_FINAL;
  void MergeFrom(const ::google::protobuf::Message& from) PROTOBUF_FINAL;
  void CopyFrom(const Request& from);
  void MergeFrom(const Request& from);
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
  void InternalSwap(Request* other);
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


  typedef Request_Method Method;
  static const Method startActivity =
    Request_Method_startActivity;
  static const Method startService =
    Request_Method_startService;
  static const Method sendBroadcast =
    Request_Method_sendBroadcast;
  static inline bool Method_IsValid(int value) {
    return Request_Method_IsValid(value);
  }
  static const Method Method_MIN =
    Request_Method_Method_MIN;
  static const Method Method_MAX =
    Request_Method_Method_MAX;
  static const int Method_ARRAYSIZE =
    Request_Method_Method_ARRAYSIZE;
  static inline const ::google::protobuf::EnumDescriptor*
  Method_descriptor() {
    return Request_Method_descriptor();
  }
  static inline const ::std::string& Method_Name(Method value) {
    return Request_Method_Name(value);
  }
  static inline bool Method_Parse(const ::std::string& name,
      Method* value) {
    return Request_Method_Parse(name, value);
  }

  // accessors -------------------------------------------------------

  // repeated string categories = 5;
  int categories_size() const;
  void clear_categories();
  static const int kCategoriesFieldNumber = 5;
  const ::std::string& categories(int index) const;
  ::std::string* mutable_categories(int index);
  void set_categories(int index, const ::std::string& value);
  #if LANG_CXX11
  void set_categories(int index, ::std::string&& value);
  #endif
  void set_categories(int index, const char* value);
  void set_categories(int index, const char* value, size_t size);
  ::std::string* add_categories();
  void add_categories(const ::std::string& value);
  #if LANG_CXX11
  void add_categories(::std::string&& value);
  #endif
  void add_categories(const char* value);
  void add_categories(const char* value, size_t size);
  const ::google::protobuf::RepeatedPtrField< ::std::string>& categories() const;
  ::google::protobuf::RepeatedPtrField< ::std::string>* mutable_categories();

  // map<string, string> extras = 7;
  int extras_size() const;
  void clear_extras();
  static const int kExtrasFieldNumber = 7;
  const ::google::protobuf::Map< ::std::string, ::std::string >&
      extras() const;
  ::google::protobuf::Map< ::std::string, ::std::string >*
      mutable_extras();

  // string action = 1;
  void clear_action();
  static const int kActionFieldNumber = 1;
  const ::std::string& action() const;
  void set_action(const ::std::string& value);
  #if LANG_CXX11
  void set_action(::std::string&& value);
  #endif
  void set_action(const char* value);
  void set_action(const char* value, size_t size);
  ::std::string* mutable_action();
  ::std::string* release_action();
  void set_allocated_action(::std::string* action);

  // string className = 2;
  void clear_classname();
  static const int kClassNameFieldNumber = 2;
  const ::std::string& classname() const;
  void set_classname(const ::std::string& value);
  #if LANG_CXX11
  void set_classname(::std::string&& value);
  #endif
  void set_classname(const char* value);
  void set_classname(const char* value, size_t size);
  ::std::string* mutable_classname();
  ::std::string* release_classname();
  void set_allocated_classname(::std::string* classname);

  // string packageName = 3;
  void clear_packagename();
  static const int kPackageNameFieldNumber = 3;
  const ::std::string& packagename() const;
  void set_packagename(const ::std::string& value);
  #if LANG_CXX11
  void set_packagename(::std::string&& value);
  #endif
  void set_packagename(const char* value);
  void set_packagename(const char* value, size_t size);
  ::std::string* mutable_packagename();
  ::std::string* release_packagename();
  void set_allocated_packagename(::std::string* packagename);

  // string type = 4;
  void clear_type();
  static const int kTypeFieldNumber = 4;
  const ::std::string& type() const;
  void set_type(const ::std::string& value);
  #if LANG_CXX11
  void set_type(::std::string&& value);
  #endif
  void set_type(const char* value);
  void set_type(const char* value, size_t size);
  ::std::string* mutable_type();
  ::std::string* release_type();
  void set_allocated_type(::std::string* type);

  // string uri = 6;
  void clear_uri();
  static const int kUriFieldNumber = 6;
  const ::std::string& uri() const;
  void set_uri(const ::std::string& value);
  #if LANG_CXX11
  void set_uri(::std::string&& value);
  #endif
  void set_uri(const char* value);
  void set_uri(const char* value, size_t size);
  ::std::string* mutable_uri();
  ::std::string* release_uri();
  void set_allocated_uri(::std::string* uri);

  // .Request.Method method = 8;
  void clear_method();
  static const int kMethodFieldNumber = 8;
  ::Request_Method method() const;
  void set_method(::Request_Method value);

  // @@protoc_insertion_point(class_scope:Request)
 private:

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  ::google::protobuf::RepeatedPtrField< ::std::string> categories_;
  ::google::protobuf::internal::MapField<
      Request_ExtrasEntry_DoNotUse,
      ::std::string, ::std::string,
      ::google::protobuf::internal::WireFormatLite::TYPE_STRING,
      ::google::protobuf::internal::WireFormatLite::TYPE_STRING,
      0 > extras_;
  ::google::protobuf::internal::ArenaStringPtr action_;
  ::google::protobuf::internal::ArenaStringPtr classname_;
  ::google::protobuf::internal::ArenaStringPtr packagename_;
  ::google::protobuf::internal::ArenaStringPtr type_;
  ::google::protobuf::internal::ArenaStringPtr uri_;
  int method_;
  mutable int _cached_size_;
  friend struct ::protobuf_intent_2eproto::TableStruct;
  friend void ::protobuf_intent_2eproto::InitDefaultsRequestImpl();
};
// ===================================================================


// ===================================================================

#ifdef __GNUC__
  #pragma GCC diagnostic push
  #pragma GCC diagnostic ignored "-Wstrict-aliasing"
#endif  // __GNUC__
// -------------------------------------------------------------------

// Request

// .Request.Method method = 8;
inline void Request::clear_method() {
  method_ = 0;
}
inline ::Request_Method Request::method() const {
  // @@protoc_insertion_point(field_get:Request.method)
  return static_cast< ::Request_Method >(method_);
}
inline void Request::set_method(::Request_Method value) {
  
  method_ = value;
  // @@protoc_insertion_point(field_set:Request.method)
}

// string action = 1;
inline void Request::clear_action() {
  action_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& Request::action() const {
  // @@protoc_insertion_point(field_get:Request.action)
  return action_.GetNoArena();
}
inline void Request::set_action(const ::std::string& value) {
  
  action_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:Request.action)
}
#if LANG_CXX11
inline void Request::set_action(::std::string&& value) {
  
  action_.SetNoArena(
    &::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::move(value));
  // @@protoc_insertion_point(field_set_rvalue:Request.action)
}
#endif
inline void Request::set_action(const char* value) {
  GOOGLE_DCHECK(value != NULL);
  
  action_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:Request.action)
}
inline void Request::set_action(const char* value, size_t size) {
  
  action_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:Request.action)
}
inline ::std::string* Request::mutable_action() {
  
  // @@protoc_insertion_point(field_mutable:Request.action)
  return action_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* Request::release_action() {
  // @@protoc_insertion_point(field_release:Request.action)
  
  return action_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void Request::set_allocated_action(::std::string* action) {
  if (action != NULL) {
    
  } else {
    
  }
  action_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), action);
  // @@protoc_insertion_point(field_set_allocated:Request.action)
}

// string className = 2;
inline void Request::clear_classname() {
  classname_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& Request::classname() const {
  // @@protoc_insertion_point(field_get:Request.className)
  return classname_.GetNoArena();
}
inline void Request::set_classname(const ::std::string& value) {
  
  classname_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:Request.className)
}
#if LANG_CXX11
inline void Request::set_classname(::std::string&& value) {
  
  classname_.SetNoArena(
    &::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::move(value));
  // @@protoc_insertion_point(field_set_rvalue:Request.className)
}
#endif
inline void Request::set_classname(const char* value) {
  GOOGLE_DCHECK(value != NULL);
  
  classname_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:Request.className)
}
inline void Request::set_classname(const char* value, size_t size) {
  
  classname_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:Request.className)
}
inline ::std::string* Request::mutable_classname() {
  
  // @@protoc_insertion_point(field_mutable:Request.className)
  return classname_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* Request::release_classname() {
  // @@protoc_insertion_point(field_release:Request.className)
  
  return classname_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void Request::set_allocated_classname(::std::string* classname) {
  if (classname != NULL) {
    
  } else {
    
  }
  classname_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), classname);
  // @@protoc_insertion_point(field_set_allocated:Request.className)
}

// string packageName = 3;
inline void Request::clear_packagename() {
  packagename_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& Request::packagename() const {
  // @@protoc_insertion_point(field_get:Request.packageName)
  return packagename_.GetNoArena();
}
inline void Request::set_packagename(const ::std::string& value) {
  
  packagename_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:Request.packageName)
}
#if LANG_CXX11
inline void Request::set_packagename(::std::string&& value) {
  
  packagename_.SetNoArena(
    &::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::move(value));
  // @@protoc_insertion_point(field_set_rvalue:Request.packageName)
}
#endif
inline void Request::set_packagename(const char* value) {
  GOOGLE_DCHECK(value != NULL);
  
  packagename_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:Request.packageName)
}
inline void Request::set_packagename(const char* value, size_t size) {
  
  packagename_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:Request.packageName)
}
inline ::std::string* Request::mutable_packagename() {
  
  // @@protoc_insertion_point(field_mutable:Request.packageName)
  return packagename_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* Request::release_packagename() {
  // @@protoc_insertion_point(field_release:Request.packageName)
  
  return packagename_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void Request::set_allocated_packagename(::std::string* packagename) {
  if (packagename != NULL) {
    
  } else {
    
  }
  packagename_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), packagename);
  // @@protoc_insertion_point(field_set_allocated:Request.packageName)
}

// string type = 4;
inline void Request::clear_type() {
  type_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& Request::type() const {
  // @@protoc_insertion_point(field_get:Request.type)
  return type_.GetNoArena();
}
inline void Request::set_type(const ::std::string& value) {
  
  type_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:Request.type)
}
#if LANG_CXX11
inline void Request::set_type(::std::string&& value) {
  
  type_.SetNoArena(
    &::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::move(value));
  // @@protoc_insertion_point(field_set_rvalue:Request.type)
}
#endif
inline void Request::set_type(const char* value) {
  GOOGLE_DCHECK(value != NULL);
  
  type_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:Request.type)
}
inline void Request::set_type(const char* value, size_t size) {
  
  type_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:Request.type)
}
inline ::std::string* Request::mutable_type() {
  
  // @@protoc_insertion_point(field_mutable:Request.type)
  return type_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* Request::release_type() {
  // @@protoc_insertion_point(field_release:Request.type)
  
  return type_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void Request::set_allocated_type(::std::string* type) {
  if (type != NULL) {
    
  } else {
    
  }
  type_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), type);
  // @@protoc_insertion_point(field_set_allocated:Request.type)
}

// repeated string categories = 5;
inline int Request::categories_size() const {
  return categories_.size();
}
inline void Request::clear_categories() {
  categories_.Clear();
}
inline const ::std::string& Request::categories(int index) const {
  // @@protoc_insertion_point(field_get:Request.categories)
  return categories_.Get(index);
}
inline ::std::string* Request::mutable_categories(int index) {
  // @@protoc_insertion_point(field_mutable:Request.categories)
  return categories_.Mutable(index);
}
inline void Request::set_categories(int index, const ::std::string& value) {
  // @@protoc_insertion_point(field_set:Request.categories)
  categories_.Mutable(index)->assign(value);
}
#if LANG_CXX11
inline void Request::set_categories(int index, ::std::string&& value) {
  // @@protoc_insertion_point(field_set:Request.categories)
  categories_.Mutable(index)->assign(std::move(value));
}
#endif
inline void Request::set_categories(int index, const char* value) {
  GOOGLE_DCHECK(value != NULL);
  categories_.Mutable(index)->assign(value);
  // @@protoc_insertion_point(field_set_char:Request.categories)
}
inline void Request::set_categories(int index, const char* value, size_t size) {
  categories_.Mutable(index)->assign(
    reinterpret_cast<const char*>(value), size);
  // @@protoc_insertion_point(field_set_pointer:Request.categories)
}
inline ::std::string* Request::add_categories() {
  // @@protoc_insertion_point(field_add_mutable:Request.categories)
  return categories_.Add();
}
inline void Request::add_categories(const ::std::string& value) {
  categories_.Add()->assign(value);
  // @@protoc_insertion_point(field_add:Request.categories)
}
#if LANG_CXX11
inline void Request::add_categories(::std::string&& value) {
  categories_.Add(std::move(value));
  // @@protoc_insertion_point(field_add:Request.categories)
}
#endif
inline void Request::add_categories(const char* value) {
  GOOGLE_DCHECK(value != NULL);
  categories_.Add()->assign(value);
  // @@protoc_insertion_point(field_add_char:Request.categories)
}
inline void Request::add_categories(const char* value, size_t size) {
  categories_.Add()->assign(reinterpret_cast<const char*>(value), size);
  // @@protoc_insertion_point(field_add_pointer:Request.categories)
}
inline const ::google::protobuf::RepeatedPtrField< ::std::string>&
Request::categories() const {
  // @@protoc_insertion_point(field_list:Request.categories)
  return categories_;
}
inline ::google::protobuf::RepeatedPtrField< ::std::string>*
Request::mutable_categories() {
  // @@protoc_insertion_point(field_mutable_list:Request.categories)
  return &categories_;
}

// string uri = 6;
inline void Request::clear_uri() {
  uri_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& Request::uri() const {
  // @@protoc_insertion_point(field_get:Request.uri)
  return uri_.GetNoArena();
}
inline void Request::set_uri(const ::std::string& value) {
  
  uri_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:Request.uri)
}
#if LANG_CXX11
inline void Request::set_uri(::std::string&& value) {
  
  uri_.SetNoArena(
    &::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::move(value));
  // @@protoc_insertion_point(field_set_rvalue:Request.uri)
}
#endif
inline void Request::set_uri(const char* value) {
  GOOGLE_DCHECK(value != NULL);
  
  uri_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:Request.uri)
}
inline void Request::set_uri(const char* value, size_t size) {
  
  uri_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:Request.uri)
}
inline ::std::string* Request::mutable_uri() {
  
  // @@protoc_insertion_point(field_mutable:Request.uri)
  return uri_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* Request::release_uri() {
  // @@protoc_insertion_point(field_release:Request.uri)
  
  return uri_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void Request::set_allocated_uri(::std::string* uri) {
  if (uri != NULL) {
    
  } else {
    
  }
  uri_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), uri);
  // @@protoc_insertion_point(field_set_allocated:Request.uri)
}

// map<string, string> extras = 7;
inline int Request::extras_size() const {
  return extras_.size();
}
inline void Request::clear_extras() {
  extras_.Clear();
}
inline const ::google::protobuf::Map< ::std::string, ::std::string >&
Request::extras() const {
  // @@protoc_insertion_point(field_map:Request.extras)
  return extras_.GetMap();
}
inline ::google::protobuf::Map< ::std::string, ::std::string >*
Request::mutable_extras() {
  // @@protoc_insertion_point(field_mutable_map:Request.extras)
  return extras_.MutableMap();
}

#ifdef __GNUC__
  #pragma GCC diagnostic pop
#endif  // __GNUC__
// -------------------------------------------------------------------


// @@protoc_insertion_point(namespace_scope)


namespace google {
namespace protobuf {

template <> struct is_proto_enum< ::Request_Method> : ::google::protobuf::internal::true_type {};
template <>
inline const EnumDescriptor* GetEnumDescriptor< ::Request_Method>() {
  return ::Request_Method_descriptor();
}

}  // namespace protobuf
}  // namespace google

// @@protoc_insertion_point(global_scope)

#endif  // PROTOBUF_intent_2eproto__INCLUDED
