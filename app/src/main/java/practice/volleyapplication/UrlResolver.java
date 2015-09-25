package practice.volleyapplication;

import android.util.SparseArray;

/**
 * Created by aasaqt on 22/9/15.
 */
public class UrlResolver {
    public static final String BASE_URL = "http://thethemestore.co";
    public static SparseArray<String> endPointMapper = null;

    public static final String withAppendedPath(int endPoint) {
        if (endPointMapper == null)
            populateMapper();
        return BASE_URL + endPointMapper.get(endPoint);

    }

    private static void populateMapper() {
        endPointMapper = new SparseArray<String>();
        endPointMapper.put(EndPoints.NEWS, "/VGUploadNews/newsAPI.php");

    }

    public interface EndPoints {
        int NONE = -1;
        int NEWS = 0;

    }
}
