Pienimuotoinen task list, laajennettu navigoinnilla.

FEATURET:

- Tasklist
- Taskin lisäys (AlertDialog)
- Taskien merkintä tehdyksi
- Taskin poisto
- Taskien suodatus (vain tehdyt)
- Taskien sorttaus due daten mukaan
- Taskin muokkaus ja poisto DetailDialogissa
- Navigointi näkymien välillä (Home ↔ Calendar ↔ Settings)

Navigointi Jetpack Composessa tarkoittaa sitä, että sovelluksessa liikutaan eri näkymien (Composable-funktioiden) välillä NavControllerin avulla ilman useita Activityjä. Sovellus käyttää Single-Activity-arkkitehtuuria, eli kaikki näkymät ovat saman MainActivityn sisällä. NavController huolehtii siitä, missä näkymässä käyttäjä on ja minne siirrytään seuraavaksi. NavHost määrittelee kaikki sovelluksen reitit (esim. home, calendar ja settings) ja kertoo, mikä näkymä näytetään milläkin reitillä.

Tässä sovelluksessa navigointi on toteutettu HomeScreenin, CalendarScreenin ja SettingsScreenin välillä nappien avulla. HomeScreeniltä voi siirtyä CalendarScreeniin ja SettingsScreeniin, ja takaisin siirtyminen hoidetaan popBackStack()-kutsulla, jolloin myös järjestelmän back-nappi toimii oikein.

Rakenne:

- UI (Compose-näkymät: HomeScreen, CalendarScreen, SettingsScreen)
- ViewModel (TaskViewModel)
- Domain (Task-data ja listan käsittelyfunktiot)

Sovelluksessa käytetään MVVM-arkkitehtuuria. TaskViewModel luodaan MainActivityssä ja jaetaan sekä HomeScreenille että CalendarScreenille. Näin molemmat näkymät käyttävät samaa tilaa, eikä ViewModelia luoda uudestaan navigoinnin yhteydessä.

ViewModelissa säilytetään task-lista, ja UI kuuntelee tätä tilaa. Kun tehtävää lisätään, muokataan tai poistetaan toisessa näkymässä, muutos näkyy heti myös toisessa näkymässä. ViewModelia käytetään rememberin sijaan, koska remember menettää tilansa, kun näkymä rakennetaan uudelleen, mutta ViewModel säilyttää tilan koko sovelluksen elinkaaren ajan ja navigoinnista huolimatta.

CalendarScreen näyttää tehtävät kalenterimaisessa näkymässä ryhmiteltynä due daten mukaan. Jokainen päivämäärä näytetään otsikkona, ja sen alle listataan kyseiseen päivään kuuluvat tehtävät. Tehtävää painamalla avautuu sama DetailDialog kuin HomeScreenissä, jolloin tehtävää voi muokata tai poistaa suoraan kalenterinäkymästä.

Taskin lisäys ja muokkaus hoidetaan AlertDialogilla, ei erillisillä navigaatiokohteilla. Taskin lisäys tapahtuu HomeScreenissä Add-nappia painamalla, jolloin avautuu AlertDialog, jossa syötetään vähintään taskin title. Tallennus kutsuu ViewModelin addTask-funktiota ja peruuta sulkee dialogin ilman muutoksia.

Taskin muokkaus tapahtuu painamalla taskia joko HomeScreenissä tai CalendarScreenissä, jolloin avautuu DetailDialog esitäytetyillä tiedoilla. Dialogissa taskin voi tallentaa (updateTask), poistaa (removeTask) tai peruuttaa ilman muutoksia. Lisäys ja muokkaus eivät ole omia navigaatiokohteitaan, vaan pelkkiä UI-dialogeja.
