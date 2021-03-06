package net.minecraft.server.management;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.util.JsonUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserList<K, V extends UserListEntry<K>> {
   protected static final Logger LOGGER = LogManager.getLogger();
   protected final Gson gson;
   private final File saveFile;
   private final Map<String, V> values = Maps.newHashMap();
   private boolean lanServer = true;
   private static final ParameterizedType USER_LIST_ENTRY_TYPE = new ParameterizedType() {
      public Type[] getActualTypeArguments() {
         return new Type[]{UserListEntry.class};
      }

      public Type getRawType() {
         return List.class;
      }

      public Type getOwnerType() {
         return null;
      }
   };

   public UserList(File p_i1144_1_) {
      this.saveFile = p_i1144_1_;
      GsonBuilder gsonbuilder = (new GsonBuilder()).setPrettyPrinting();
      gsonbuilder.registerTypeHierarchyAdapter(UserListEntry.class, new UserList.Serializer());
      this.gson = gsonbuilder.create();
   }

   public boolean isLanServer() {
      return this.lanServer;
   }

   public void setLanServer(boolean p_152686_1_) {
      this.lanServer = p_152686_1_;
   }

   public File getSaveFile() {
      return this.saveFile;
   }

   public void addEntry(V p_152687_1_) {
      this.values.put(this.getObjectKey(p_152687_1_.getValue()), p_152687_1_);

      try {
         this.writeChanges();
      } catch (IOException ioexception) {
         LOGGER.warn("Could not save the list after adding a user.", ioexception);
      }

   }

   @Nullable
   public V getEntry(K p_152683_1_) {
      this.removeExpired();
      return this.values.get(this.getObjectKey(p_152683_1_));
   }

   public void removeEntry(K p_152684_1_) {
      this.values.remove(this.getObjectKey(p_152684_1_));

      try {
         this.writeChanges();
      } catch (IOException ioexception) {
         LOGGER.warn("Could not save the list after removing a user.", ioexception);
      }

   }

   public void func_199042_b(UserListEntry<K> p_199042_1_) {
      this.removeEntry(p_199042_1_.getValue());
   }

   public String[] getKeys() {
      return this.values.keySet().toArray(new String[this.values.size()]);
   }

   public boolean isEmpty() {
      return this.values.size() < 1;
   }

   protected String getObjectKey(K p_152681_1_) {
      return p_152681_1_.toString();
   }

   protected boolean hasEntry(K p_152692_1_) {
      return this.values.containsKey(this.getObjectKey(p_152692_1_));
   }

   private void removeExpired() {
      List<K> list = Lists.newArrayList();

      for(V v : this.values.values()) {
         if (v.hasBanExpired()) {
            list.add(v.getValue());
         }
      }

      for(K k : list) {
         this.values.remove(this.getObjectKey(k));
      }

   }

   protected UserListEntry<K> createEntry(JsonObject p_152682_1_) {
      return new UserListEntry<>(null, p_152682_1_);
   }

   public Collection<V> func_199043_f() {
      return this.values.values();
   }

   public void writeChanges() throws IOException {
      Collection<V> collection = this.values.values();
      String s = this.gson.toJson(collection);
      BufferedWriter bufferedwriter = null;

      try {
         bufferedwriter = Files.newWriter(this.saveFile, StandardCharsets.UTF_8);
         bufferedwriter.write(s);
      } finally {
         IOUtils.closeQuietly(bufferedwriter);
      }

   }

   public void readSavedFile() throws FileNotFoundException {
      if (this.saveFile.exists()) {
         BufferedReader bufferedreader = null;

         try {
            bufferedreader = Files.newReader(this.saveFile, StandardCharsets.UTF_8);
            Collection<UserListEntry<K>> collection = JsonUtils.fromJson(this.gson, bufferedreader, USER_LIST_ENTRY_TYPE);
            if (collection != null) {
               this.values.clear();

               for(UserListEntry<K> userlistentry : collection) {
                  if (userlistentry.getValue() != null) {
                     this.values.put(this.getObjectKey(userlistentry.getValue()), (V)userlistentry);
                  }
               }
            }
         } finally {
            IOUtils.closeQuietly(bufferedreader);
         }

      }
   }

   class Serializer implements JsonDeserializer<UserListEntry<K>>, JsonSerializer<UserListEntry<K>> {
      private Serializer() {
      }

      public JsonElement serialize(UserListEntry<K> p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
         JsonObject jsonobject = new JsonObject();
         p_serialize_1_.onSerialization(jsonobject);
         return jsonobject;
      }

      public UserListEntry<K> deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         if (p_deserialize_1_.isJsonObject()) {
            JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
            return UserList.this.createEntry(jsonobject);
         } else {
            return null;
         }
      }
   }
}
