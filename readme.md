LOKALNA INSTALACIJA:

1. preuzeti i instalirati Node.js
    preporučeno msi installer za Windowse
    (https://nodejs.org/en/download/)
    
2. preuzeti i instalirati MySQL server i Workbench
    preporučano preko web installera, odabrati samo dvije gore spomenute komponente (ostale nam nisu potrebne)
    (https://dev.mysql.com/downloads/installer/, tj. https://dev.mysql.com/downloads/file/?id=459895)
    
3. U workbenchu izvršiti SQL kôd iz datoteke sql.sql kako bi se formirala početna baza s tablicama naziva drustvena_mreza

4. Provjeriti odgovara li port MySQL servera onome u config/db.js, ako ne, promijeniti taj parametar

5. U direktoriju gdje je smješten repozitorij otvoriti Command Prompt / Terminal i naredbom "node server.js" pokrenuti

6. U web pregledniku upisati "localhost:8080/index"