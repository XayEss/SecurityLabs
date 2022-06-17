package web.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.stereotype.Component;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.kms.v1.CryptoKey;
import com.google.cloud.kms.v1.CryptoKey.CryptoKeyPurpose;
import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.CryptoKeyVersion.CryptoKeyVersionAlgorithm;
import com.google.cloud.kms.v1.CryptoKeyVersionTemplate;
import com.google.cloud.kms.v1.DecryptResponse;
import com.google.cloud.kms.v1.EncryptResponse;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyManagementServiceClient.ListKeyRingsPagedResponse;
import com.google.cloud.kms.v1.KeyRing;
import com.google.cloud.kms.v1.KeyRingName;
import com.google.cloud.kms.v1.LocationName;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;
import com.google.protobuf.ByteString;

@Component
public class GoogleKMS {
	private String projectId = "mysecurityproject-341912";
	private String locationId = "us-east1";
	private String id = "my-key";
	private String keyRingId = "107284941970588444196";
	
	public void authExplicit() {
		String jsonPath = "src/main/webapp/WEB-INF/configs/mysecurityproject-341912-c395b662b62b.json";
		GoogleCredentials credentials = null;
		try {
			credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath))
			        .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		  Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
	}
	
	public void initKeys() {
		projectId = "mysecurityproject-341912";
		locationId = "us-east1";	
		keyRingId = "107284941970588444196";
		id = "my-key";
	}
	
	public void createKey() {
		try (KeyManagementServiceClient client = KeyManagementServiceClient.create()) {
			KeyRingName keyRingName = KeyRingName.of(projectId, locationId, keyRingId);
			CryptoKey key =
          CryptoKey.newBuilder()
              .setPurpose(CryptoKeyPurpose.ENCRYPT_DECRYPT)
              .setVersionTemplate(
                  CryptoKeyVersionTemplate.newBuilder()
                      .setAlgorithm(CryptoKeyVersionAlgorithm.GOOGLE_SYMMETRIC_ENCRYPTION))
              .build();
			CryptoKey createdKey = client.createCryptoKey(keyRingName, id, key);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	public void createKeyRing() {
		try (KeyManagementServiceClient client = KeyManagementServiceClient.create()) {
			LocationName locationName = LocationName.of(projectId, locationId);
			// Build the key version name from the project, location, key ring, key,
			// and key version.
			KeyRing keyRing = KeyRing.newBuilder().build();
			KeyRing createdKeyRing = client.createKeyRing(locationName, id, keyRing);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String encrypt(String plainKey) {
		return encryptKey(plainKey, projectId, locationId, id, keyRingId);
	}
	
	public String decrypt(String encryptKey) {
		return decryptKey(encryptKey, projectId, locationId, id, keyRingId);
	}
	
	public byte[] encryptB(byte[] plainKey) {
		return encryptKeyB(plainKey, projectId, locationId, id, keyRingId);
	}
	
	public byte[] decrypt(byte[] encryptKey) {
		return decryptKey(encryptKey, projectId, locationId, id, keyRingId);
	}

	public String encryptKey(String plainKey, String projectId, String locationId, String keyId, String keyRingId) {
		try (KeyManagementServiceClient client = KeyManagementServiceClient.create()) {
			CryptoKeyName keyVersionName = CryptoKeyName.of(projectId, locationId, keyRingId, keyId);
			EncryptResponse response = client.encrypt(keyVersionName, ByteString.copyFromUtf8(plainKey));
			return Base64.getEncoder().encodeToString(response.getCiphertext().toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public byte[] encryptKeyB(byte[] plainKey, String projectId, String locationId, String keyId, String keyRingId) {
		try (KeyManagementServiceClient client = KeyManagementServiceClient.create()) {
			CryptoKeyName keyVersionName = CryptoKeyName.of(projectId, locationId, keyRingId, keyId);
			EncryptResponse response = client.encrypt(keyVersionName, ByteString.copyFrom(plainKey));
			return response.getCiphertext().toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String decryptKey(String encKey, String projectId, String locationId, String keyId, String keyRingId) {
		try (KeyManagementServiceClient client = KeyManagementServiceClient.create()) {
			CryptoKeyName keyName = CryptoKeyName.of(projectId, locationId, keyRingId, keyId);
			DecryptResponse response = client.decrypt(keyName, ByteString.copyFromUtf8(encKey));
			return response.getPlaintext().toStringUtf8();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public byte[] decryptKey(byte[] encKey, String projectId, String locationId, String keyId, String keyRingId) {
		try (KeyManagementServiceClient client = KeyManagementServiceClient.create()) {
			CryptoKeyName keyName = CryptoKeyName.of(projectId, locationId, keyRingId, keyId);
			DecryptResponse response = client.decrypt(keyName, ByteString.copyFrom(encKey));
			return response.getPlaintext().toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
