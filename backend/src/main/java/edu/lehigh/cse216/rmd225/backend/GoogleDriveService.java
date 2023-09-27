package edu.lehigh.cse216.rmd225.backend;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.drive.model.PermissionList;
import org.apache.tika.Tika;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

import java.util.Collections;


public class GoogleDriveService {
    private final String APPLICATION_NAME = "team-6-382200"; // ?? is project name same as application name?
    private final String SERVICE_ACCOUNT_JSON = "team-6-382200-51d2a7bb090f.json";
    private final String SERVICE_ACCOUNT_EMAIL = "id-ware-engineers@team-6-382200.iam.gserviceaccount.com";

    private Drive driveService;
    private Drive personalDrive;

    public GoogleDriveService() throws IOException, GeneralSecurityException {
        InputStream serviceAccount = new FileInputStream(SERVICE_ACCOUNT_JSON);
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount)
                .createScoped(Collections.singleton(DriveScopes.DRIVE));
        credentials = credentials.createDelegated(SERVICE_ACCOUNT_EMAIL);
        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);
        this.driveService = new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                requestInitializer
        ).setApplicationName(APPLICATION_NAME).build();

        /* FileList result = driveService.files().list()
                .setPageSize(5)
                .setFields("nextPageToken, files(name)")
                .execute();
        for (File file : result.getFiles()) {
            System.out.printf("Found file: %s\n", file.getName());
        } */

    }

    public String uploadFile(byte[] fileBytes, String fileName) throws IOException {
        File fileMetadata = new File();
        Tika tika = new Tika();
        String mimeType = tika.detect(fileBytes);
        System.out.println(mimeType);
        fileMetadata.setName(fileName);
        ByteArrayContent content = new ByteArrayContent(mimeType, fileBytes); // application/octet-system // image/jpeg // image/jpg
        File uploadedFile = driveService.files().create(fileMetadata, content)
                .setFields("id")
                .execute();
        String fileId = uploadedFile.getId();
        // Create a permission for the file with the appropriate access level.
        Permission permission = new Permission()
                                .setType("anyone")
                                .setRole("reader");
        // Add the permission to the file.
        Drive.Permissions.Create createPermission = driveService.permissions().create(fileId, permission);
        createPermission.execute();
        // Get the file resource with the WebViewLink property
        uploadedFile = driveService.files().get(fileId).setFields("webViewLink").execute();

        // Get the URL from the WebViewLink property
        String url = uploadedFile.getWebViewLink();
        return url;
    }

    /**
     * Retrieve the content of a Google Drive folder using the specified Drive service object.
     *
     * @param service  the Drive service object.
     * @param folderId the ID of the Google Drive folder to retrieve content from.
     * @return a list of File objects representing the content of the specified folder.
     * @throws IOException if an error occurs while retrieving the folder content.
     */
    public static FileList retrieveFolderContent(Drive service, String folderId) throws IOException {
        FileList result = service.files().list()
                .setQ(String.format("'%s' in parents and trashed = false", folderId))
                .setFields("nextPageToken, files(id, name)")
                .execute();
        return result;
    }

    private static GoogleCredentials getServiceAccountCredential() throws IOException {
        GoogleCredentials credential = GoogleCredentials.fromStream(GoogleDriveService.class.getResourceAsStream("team-6-382200-51d2a7bb090f.json"))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));
        return credential;
    }

}