package de.meditrack;

import java.util.List;

public interface MedikamenteUI {
        void aktualisiereMedikamentenliste(List<String> medikamentenStrings);
        void aktualisiereBaldAblaufendeMedikamente(List<String> medikamentenStrings);
        void aktualisiereAbgelaufeneMedikamente(List<String> medikamentenStrings);
}
