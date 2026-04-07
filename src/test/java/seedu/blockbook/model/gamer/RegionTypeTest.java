package seedu.blockbook.model.gamer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class RegionTypeTest {

    @Test
    public void values_containsAllSupportedRegions() {
        assertEquals(
                List.of(
                        RegionType.NA,
                        RegionType.SA,
                        RegionType.EU,
                        RegionType.AFRICA,
                        RegionType.ASIA,
                        RegionType.OCEANIA,
                        RegionType.ME),
                List.of(RegionType.values()));
    }
}
