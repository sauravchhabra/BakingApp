package android.sauravchhabra.com.bakingapp.data.local;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

@Database(fileName = BakingDatabase.NAME, version = BakingDatabase.VERSION)
class BakingDatabase {
    static final String NAME = "bakingDatabase";
    static final int VERSION = 1;

    @Table(BakingContract.class)
    static final String TABLE = "ingredients";
}
