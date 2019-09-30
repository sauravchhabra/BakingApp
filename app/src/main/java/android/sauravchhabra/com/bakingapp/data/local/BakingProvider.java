package android.sauravchhabra.com.bakingapp.data.local;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

@ContentProvider(authority = BakingProvider.PROVIDER, database = BakingDatabase.class)
public class BakingProvider {

    static final String PROVIDER = "android.sauravchhabra.com.bakingapp.local.provider";

    @TableEndpoint(table = BakingDatabase.TABLE)
    public static class Recipe{
        @ContentUri(path = "ingredients", type = "vnd.android.cursor.dir/ingredients")
        public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER + "/ingredients");
    }
}
