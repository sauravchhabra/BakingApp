package nanodegree.sauravchhabra.com.bakingapp.data.local;


import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.ConflictResolutionType;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.UniqueConstraint;

@UniqueConstraint(columns = {BakingContract.RECIPE_NAME}, onConflict = ConflictResolutionType.REPLACE)
public class BakingContract {

    @DataType(DataType.Type.INTEGER)
    @PrimaryKey(onConflict = ConflictResolutionType.REPLACE)
    @AutoIncrement
    public static final String COLUMN_ID = "_id";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String RECIPE_NAME = "recipeName";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String QUANTITY_MEASURE = "quantityMeasure";
}
