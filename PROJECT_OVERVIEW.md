# Frugo backend

## Účel

Backend systému Frugo pro správu skladu a návazných provozních procesů. Poskytuje REST API pro backoffice a vybrané funkce zákaznického obchodu. Doménové oblasti zahrnují produkty a skladové položky, sklady a lokace, palety, objednávky a expedice, nákupní objednávky a příjem zboží, zákazníky, dodavatele, zaměstnance a číselníky. Změny skladu a stavů se zaznamenávají prostřednictvím pohybů, event logu a historie stavů.

## Technologie

- Java 17 a Spring Boot (verze parentu je definována v `pom.xml`).
- Spring Web, Spring Data JPA, Spring Security a Flyway.
- PostgreSQL jako provozní databáze; lokální kontejner je definován v `compose.yaml`.
- JWT (jjwt) pro bezstavové přihlášení a autorizaci, BCrypt pro hesla.
- Thymeleaf šablony a OpenHTMLtoPDF pro generování PDF; ZXing pro QR/čárové kódy.
- Maven Wrapper (`mvnw` / `mvnw.cmd`) pro sestavení a spuštění.

## Struktura zdrojů

- `src/main/java/com/lulak/frugo/controller`: HTTP rozhraní rozdělené na `admin`, `auth` a `shop`.
- `service`: aplikační logika a transakční operace; například skladové přesuny/rozdělení, příjem nákupní objednávky a evidence událostí.
- `model`: JPA entity podle doménových oblastí.
- `repository`: Spring Data přístup k datům.
- `dto`: vstupní a výstupní kontrakty API.
- `security`: konfigurace Spring Security, JWT filtr a práce s tokeny/hesly.
- `src/main/resources/db/migration`: verzované SQL migrace Flyway (aktuálně V1–V14).
- `src/main/resources/templates`: HTML šablony PDF dokumentů; `fonts` a `static` obsahují podpůrné zdroje.
- `src/test`: testy a testovací konfigurace.

## API a zabezpečení

- Přihlášení je dostupné na `POST /api/auth/login`. Úspěšná odpověď obsahuje JWT, role, oprávnění a uživatelské jméno.
- Administrátorské endpointy jsou pod `/api/admin/**`; obchodní endpointy pod `/api/shop/**`.
- Zabezpečení je bezstavové. Ostatní požadavky než `/api/auth/**` vyžadují autentizaci přes Bearer token. Oprávnění jsou součástí JWT; v controllerech a službách se může uplatňovat také metodová autorizace.
- CORS ve `SecurityConfig` povoluje vývojový origin `http://localhost:5173`.
- Výběr endpointů lze mapovat podle controllerů v `controller/admin`, `controller/shop` a `controller/auth`.

## Databáze a lokální spuštění

Konfigurace v `src/main/resources/application.properties` očekává PostgreSQL na `localhost:5432`, databázi `frugo` a uživatele `frugo`. Stejné vývojové hodnoty nastavuje `compose.yaml`.

```powershell
docker compose up -d postgres
./mvnw spring-boot:run
```

Ve Windows lze použít `mvnw.cmd spring-boot:run`. Flyway aplikuje migrace při startu a Hibernate schéma validuje (`ddl-auto=validate`). Pro testy existuje `src/test/resources/application-test.properties`, který používá databázi `frugo_test`; konkrétní testovací příkaz a požadavky závisí na dostupné testovací databázi.

## Konfigurace a provozní poznámky

- Připojení k databázi je nyní uvedeno přímo v `application.properties`. Před nasazením je vhodné hodnoty přesunout do proměnných prostředí nebo externí konfigurace.
- `JwtSecurity` obsahuje pevně zapsaný tajný klíč. Pro jiné než lokální prostředí musí být nahrazen bezpečně spravovaným tajemstvím.
- `spring.jpa.show-sql=true` vypisuje SQL dotazy; může vytvářet nadměrné logy.
- Migrace V3 a další obsahují vývojová/demonstrační data. Před použitím databáze mimo lokální vývoj je potřeba ověřit, které seed migrace jsou vhodné.
- Datový adresář `data/` obsahuje lokální soubory H2, ale současná hlavní konfigurace aplikace cílí na PostgreSQL.

## Testy a stav repozitáře

Testy jsou pod `src/test/java` a používají Spring Boot Test. V pracovním stromu jsou navíc necommitnuté soubory pod `src/test/java/com/lulak/frugo/service/` a `src/test/resources/`; zachovejte je při práci. Tento přehled vychází ze zdrojů a konfigurace, nikoli z provedeného buildu.
