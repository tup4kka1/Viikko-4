Pienimuotoinen task list

FEATURET:

- Tasklist
- Taskin lisäys AlertDialogilla
- Taskien merkintä tehdyksi
- Taskin poisto
- Taskien suodatus (vain tehdyt)
- Taskien sorttaus due daten mukaan
- Taskin muokkaus ja poisto DetailDialogissa
- Navigointi näkymien välillä (Home ↔ Calendar ↔ Settings)

Navigointi on tehty Jetpack Composen navigation-komponentilla. Käytössä on yksi Activity, ja eri näkymät ovat Composable-funktioita, joiden välillä liikutaan NavControllerin avulla. NavHost määrittelee reitit ja sen, mikä näkymä näytetään milläkin reitillä.

Tässä sovelluksessa käyttäjä voi siirtyä HomeScreenin, CalendarScreenin ja SettingsScreenin välillä nappeja painamalla. Takaisin siirtyminen hoidetaan popBackStack()-kutsulla, jolloin myös Androidin oma back-nappi toimii normaalisti.

Rakenne:

- UI (Compose-näkymät: HomeScreen, CalendarScreen, SettingsScreen)
- ViewModel (TaskViewModel)
- Domain (Task-data ja listan käsittelyfunktiot)

Sovelluksessa käytetään MVVM-mallia. TaskViewModel luodaan MainActivityssä ja sama ViewModel jaetaan HomeScreenille ja CalendarScreenille. Näin molemmat näkymät käyttävät samaa task-listaa, eikä ViewModelia luoda uudestaan navigoinnin yhteydessä.

ViewModelissa säilytetään sovelluksen tila, eli task-lista, ja UI kuuntelee tätä tilaa. Kun tehtävä lisätään, muokataan tai poistetaan toisessa näkymässä, muutos näkyy heti myös toisessa näkymässä. ViewModelia käytetään rememberin sijaan, koska remember menettää tilansa, kun näkymä rakennetaan uudelleen, mutta ViewModel säilyttää tilan sovelluksen elinkaaren ajan.

CalendarScreen näyttää tehtävät kalenterimaisessa näkymässä ryhmiteltynä due daten mukaan. Jokaiselle päivälle näytetään oma otsikko ja sen alle kyseisen päivän tehtävät. Tehtävää painamalla avautuu sama DetailDialog kuin HomeScreenissä, jolloin tehtävää voi muokata tai poistaa myös kalenterinäkymästä.

Taskin lisäys ja muokkaus on tehty AlertDialogeilla, eikä niitä varten ole omia navigaatioreittejä. Taskin lisäys tapahtuu HomeScreenissä Add-nappia painamalla, jolloin avautuu dialogi, jossa syötetään vähintään taskin title. Tallennus kutsuu ViewModelin addTask-funktiota ja peruuta sulkee dialogin ilman muutoksia.

Taskin muokkaus tapahtuu painamalla taskia joko HomeScreenissä tai CalendarScreenissä. Tällöin avautuu DetailDialog, jossa kentät on valmiiksi täytetty. Dialogissa taskin voi tallentaa, poistaa tai peruuttaa ilman muutoksia. Lisäys ja muokkaus ovat pelkkiä UI-dialogeja eivätkä omia navigointikohteita.
