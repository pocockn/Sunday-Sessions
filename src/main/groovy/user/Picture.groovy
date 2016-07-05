package user

import com.restfb.Facebook
import com.restfb.types.Message
import com.restfb.types.NamedFacebookType

/**
 * Created by pocockn on 05/07/16.
 */
class Picture extends NamedFacebookType {

    @Facebook("data")
    private Message.ImageData data;

    public static class ImageData {
        @Facebook
        private String url;

        @Facebook
        private int width;

        @Facebook
        private int height;

        @Facebook("is_silhouette")
        private boolean silhouette;
    }

    public String getUrl() {
        return data.url;
    }

    public int getWidth() {
        return data.width;
    }

    public int getHeight() {
        return data.height;
    }

    public boolean isSilhouette() {
        return data.silhouette;
    }
}
