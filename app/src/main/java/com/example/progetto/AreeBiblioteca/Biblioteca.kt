package com.example.progetto.AreeBiblioteca

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.progetto.dataBase.DBViewModel
import com.example.progetto.Entity.Libro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.progetto.R


class Biblioteca : AppCompatActivity() {

    private lateinit var dbViewModel: DBViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_biblioteca)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.gridLayoutBiblioteca)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val matricola = intent.getIntExtra("username", 0)
        Log.d("BiblioDebu", "Recupero della matricola: $matricola")
        val bottoneLinguistica: Button = findViewById(R.id.linguistica)
        val bottoneMatematica: Button = findViewById(R.id.matematica)
        val bottoneInformatica: Button = findViewById(R.id.informatica)
        val bottoneGiuridico: Button = findViewById(R.id.giuridica)
        val bottoneEconomico: Button = findViewById(R.id.economica)
        val bottoneStorico: Button = findViewById(R.id.storica)
        val bottoneFilosofico: Button = findViewById(R.id.filosofica)
        val bottonePsicologico: Button = findViewById(R.id.psicologica)
        val bottonePedagogico: Button = findViewById(R.id.pedagogica)

        dbViewModel = DBViewModel(application)

        // Inizio inserimento libri
        inserisciLibri()

        val linguistica: String= "linguistica"

        bottoneLinguistica.setOnClickListener {
            val intent = Intent(this, Area_Linguistica::class.java).apply {
                putExtra("area", linguistica)
                putExtra("username", matricola)  // Usa la stessa chiave "username"
                Log.d("BiblioDebu", "Passo la matricola a Area_Linguistica: $matricola")
            }
            startActivity(intent)
        }

        bottoneMatematica.setOnClickListener {
            val intent = Intent(this, Area_Matematica::class.java).apply {
                putExtra("area", "Matematica")
                putExtra("username", matricola)  // Usa la stessa chiave "username"
                Log.d("BiblioDebu", "Passo la matricola a Area_Matematica: $matricola")
            }
            startActivity(intent)
        }

        bottoneInformatica.setOnClickListener {
            val intent = Intent(this, Area_Informatica::class.java).apply {
                putExtra("area", "Informatica")
                putExtra("username", matricola)  // Usa la stessa chiave "username"
                Log.d("BiblioDebu", "Passo la matricola a Area_Informatica: $matricola")
            }
            startActivity(intent)
        }

        bottoneGiuridico.setOnClickListener {
            val intent = Intent(this, Area_Giuridica::class.java).apply {
                putExtra("area", "Giuridico")
                putExtra("username", matricola)  // Usa la stessa chiave "username"
                Log.d("BiblioDebu", "Passo la matricola a Area_Giuridica: $matricola")
            }
            startActivity(intent)
        }

        bottoneEconomico.setOnClickListener {
            val intent = Intent(this, Area_Economica::class.java).apply {
                putExtra("area", "Economica")
                putExtra("username", matricola)  // Usa la stessa chiave "username"
                Log.d("BiblioDebu", "Passo la matricola a Area_Economica: $matricola")
            }
            startActivity(intent)
        }

        bottoneStorico.setOnClickListener {
            val intent = Intent(this, Area_Storica::class.java).apply {
                putExtra("area", "Storico")
                putExtra("username", matricola)  // Usa la stessa chiave "username"
                Log.d("BiblioDebu", "Passo la matricola a Area_Storica: $matricola")
            }
            startActivity(intent)
        }

        bottoneFilosofico.setOnClickListener {
            val intent = Intent(this, Area_Filosofica::class.java).apply {
                putExtra("area", "Filosofico")
                putExtra("username", matricola)  // Usa la stessa chiave "username"
                Log.d("BiblioDebu", "Passo la matricola a Area_Filosofica: $matricola")
            }
            startActivity(intent)
        }

        bottonePsicologico.setOnClickListener {
            val intent = Intent(this, Area_Psicologica::class.java).apply {
                putExtra("area", "Psicologico")
                putExtra("username", matricola)  // Usa la stessa chiave "username"
                Log.d("BiblioDebu", "Passo la matricola a Area_Psicologica: $matricola")
            }
            startActivity(intent)
        }

        bottonePedagogico.setOnClickListener {
            val intent = Intent(this, Area_Pedagogica::class.java).apply {
                putExtra("area", "Pedagogico")
                putExtra("username", matricola)  // Usa la stessa chiave "username"
                Log.d("BiblioDebu", "Passo la matricola a Area_Pedagogica: $matricola")
            }
            startActivity(intent)
        }
    }

    private fun inserisciLibri() {

        val libri = listOf(
            Libro(name = "Il Signore degli Anelli", autore = "J.R.R. Tolkien", settore = "Fantasy", sinossi = "Un'epica avventura nella Terra di Mezzo, dove un gruppo di eroi deve distruggere un anello magico per salvare il mondo."),
            Libro(name = "Orgoglio e Pregiudizio", autore = "Jane Austen", settore = "Romanzo", sinossi = "Una storia d'amore e pregiudizi sociali tra Elizabeth Bennet e Mr. Darcy nella società inglese del XIX secolo."),
            Libro(name = "Le Cronache del Ghiaccio e del Fuoco: Il Trono di Spade", autore = "George R.R. Martin", settore = "Fantasy", sinossi = "Le lotte dinastiche per il Trono di Spade, in un mondo medievale popolato da magia, intrighi e guerre."),
            Libro(name = "1984", autore = "George Orwell", settore = "Linguistica", sinossi = "Un inquietante futuro distopico dominato da un regime totalitario che controlla ogni aspetto della vita."),
            Libro(name = "Il Piccolo Principe", autore = "Antoine de Saint-Exupéry", settore = "Linguistica", sinossi = "Un racconto poetico e filosofico sulla vita e sull'amore attraverso gli occhi di un bambino."),
            Libro(name = "Delitto e Castigo", autore = "Fëdor Dostoevskij", settore = "Classico", sinossi = "La tormentata vicenda di un giovane studente che commette un omicidio e lotta con il senso di colpa."),
            Libro(name = "Cime tempestose", autore = "Emily Brontë", settore = "Gotico", sinossi = "Una tragica storia d'amore e vendetta ambientata nelle brughiere inglesi."),
            Libro(name = "L'Odissea", autore = "Omero", settore = "Linguistica", sinossi = "Il leggendario viaggio di Ulisse per tornare a casa dopo la guerra di Troia."),
            Libro(name = "Moby Dick", autore = "Herman Melville", settore = "Avventura", sinossi = "La caccia ossessiva del capitano Achab contro la balena bianca."),
            Libro(name = "Il Processo", autore = "Franz Kafka", settore = "Linguistica", sinossi = "Un uomo viene arrestato e processato senza mai conoscere l'accusa, in un mondo assurdo e burocratico."),
            Libro(name = "Dieci piccoli indiani", autore = "Agatha Christie", settore = "Linguistica", sinossi = "Dieci sconosciuti vengono invitati su un'isola e muoiono misteriosamente uno dopo l'altro."),
            Libro(name = "Il Nome della Rosa", autore = "Umberto Eco", settore = "Storico", sinossi = "Un monaco e il suo giovane allievo indagano su una serie di omicidi in un'abbazia medievale."),
            Libro(name = "Neuromante", autore = "William Gibson", settore = "Linguistica", sinossi = "Il romanzo che ha definito il cyberpunk, tra hacker, intelligenze artificiali e realtà virtuale."),
            Libro(name = "Fondazione", autore = "Isaac Asimov", settore = "Fantascienza", sinossi = "La storia della caduta e rinascita di un impero galattico grazie alla psicostoria."),
            Libro(name = "Dune", autore = "Frank Herbert", settore = "Linguistica", sinossi = "Un'epopea fantascientifica ambientata su un pianeta desertico, tra intrighi politici e poteri mistici."),
            Libro(name = "La struttura delle lingue", autore = "Noam Chomsky", settore = "Linguistica", sinossi = "Un'analisi della grammatica universale e delle strutture linguistiche umane."),
            Libro(name = "Introduzione alla linguistica generale", autore = "Ferdinand de Saussure", settore = "Linguistica", sinossi = "Il testo fondamentale della linguistica moderna."),
            Libro(name = "Linguistica generale", autore = "Morris Halle", settore = "Linguistica", sinossi = "Una panoramica delle teorie linguistiche e delle loro applicazioni."),
            Libro(name = "Language and Mind", autore = "Noam Chomsky", settore = "Linguistica", sinossi = "Un'esplorazione della relazione tra linguaggio e pensiero umano."),
            Libro(name = "Semantica e Pragmatica", autore = "Paul Grice", settore = "Linguistica", sinossi = "Un'analisi del significato e dell'uso del linguaggio nella comunicazione."),
            Libro(name = "La lingua come sistema di segni", autore = "Ferdinand de Saussure", settore = "Linguistica", sinossi = "Una delle opere fondative della semiotica e della linguistica strutturale."),
            Libro(name = "Lingua e cultura", autore = "Edward Sapir", settore = "Linguistica", sinossi = "Un'indagine sulle interazioni tra linguaggio, cultura e pensiero."),
            Libro(name = "I segni del linguaggio", autore = "Charles Peirce", settore = "Linguistica", sinossi = "Un'introduzione alla semiotica e ai segni nel linguaggio."),
            Libro(name = "Sintassi delle lingue del mondo", autore = "Joseph Greenberg", settore = "Linguistica", sinossi = "Uno studio comparativo sulle strutture sintattiche delle lingue."),
            Libro(name = "Linguistica cognitiva", autore = "George Lakoff", settore = "Linguistica", sinossi = "Un'esplorazione del rapporto tra linguaggio e cognizione."),
            Libro(name = "Fonologia e fonetica", autore = "Peter Ladefoged", settore = "Linguistica", sinossi = "Un'analisi scientifica dei suoni del linguaggio umano."),
            Libro(name = "Lingue e linguaggi", autore = "Roman Jakobson", settore = "Linguistica", sinossi = "Uno studio sulle funzioni e le strutture del linguaggio."),
            Libro(name = "Evoluzione del linguaggio", autore = "Derek Bickerton", settore = "Linguistica", sinossi = "Un'analisi di come il linguaggio umano si sia evoluto nel tempo."),
            Libro(name = "Linguistica comparata", autore = "Franz Bopp", settore = "Linguistica", sinossi = "Uno studio comparativo sulle lingue indoeuropee."),
            Libro(name = "La grammatica universale", autore = "Noam Chomsky", settore = "Linguistica", sinossi = "La teoria della grammatica universale e delle regole innate del linguaggio."),
            Libro(name = "Teoria della comunicazione", autore = "Roman Jakobson", settore = "Linguistica", sinossi = "Un'analisi dei sei fattori della comunicazione e delle funzioni del linguaggio."),
            Libro(name = "Il significato nelle lingue", autore = "Paul Grice", settore = "Linguistica", sinossi = "Uno studio sui meccanismi del significato e dell'interpretazione nel linguaggio."),
            Libro(name = "Linguistica storica", autore = "August Schleicher", settore = "Linguistica", sinossi = "Un'esplorazione dei cambiamenti linguistici nel tempo."),
            Libro(name = "Sociolinguistica", autore = "William Labov", settore = "Linguistica", sinossi = "Un'analisi del rapporto tra lingua e società."),
            Libro(name = "Psicolinguistica", autore = "Jean Berko Gleason", settore = "Linguistica", sinossi = "Uno studio sulla relazione tra linguaggio e mente."),
            Libro(name = "Linguaggio e società", autore = "Dell Hymes", settore = "Linguistica", sinossi = "Un'indagine sociolinguistica sul linguaggio e la comunicazione."),
            Libro(name = "Il potere del linguaggio", autore = "Deborah Tannen", settore = "Linguistica", sinossi = "Un'analisi dell'influenza del linguaggio nelle relazioni sociali."),
            Libro(name = "Traduzione e linguistica", autore = "Eugene Nida", settore = "Linguistica", sinossi = "Un'introduzione alla teoria e alla pratica della traduzione."),
            Libro(name = "Grammatica del discorso", autore = "Michael Halliday", settore = "Linguistica", sinossi = "Uno studio sulla struttura del discorso."),
            Libro(name = "Lingue in contatto", autore = "Uriel Weinreich", settore = "Linguistica", sinossi = "Un'analisi del fenomeno del bilinguismo."),
            Libro(name = "Semiotica e linguistica", autore = "Umberto Eco", settore = "Linguistica", sinossi = "Un'analisi approfondita dei segni, del significato e della comunicazione nel linguaggio umano."),
            Libro(name = "Analisi del testo linguistico", autore = "Teun van Dijk", settore = "Linguistica", sinossi = "Un'esplorazione delle strategie di analisi testuale e del discorso in ambito linguistico e sociale."),
            Libro(name = "Lingue artificiali", autore = "John Quijada", settore = "Linguistica", sinossi = "Uno studio sulle lingue costruite, la loro struttura e il loro utilizzo nella comunicazione."),
            Libro(name = "Pragmatica linguistica", autore = "Stephen Levinson", settore = "Linguistica", sinossi = "Un'indagine su come il contesto influisce sul significato e sull'uso del linguaggio."),
            Libro(name = "Linguaggio e cognizione", autore = "Ray Jackendoff", settore = "Linguistica", sinossi = "Un'analisi della relazione tra struttura linguistica e processi cognitivi umani."),
            Libro(name = "Clean Code", autore = "Robert C. Martin", settore = "Informatica", sinossi = "Un libro essenziale per gli sviluppatori che vogliono scrivere codice pulito, leggibile e manutenibile."),
            Libro(name = "Introduction to Algorithms", autore = "Thomas H. Cormen", settore = "Informatica", sinossi = "Un'opera di riferimento sulle strutture dati e algoritmi, con spiegazioni dettagliate e analisi delle complessità."),
            Libro(name = "The Pragmatic Programmer", autore = "Andrew Hunt", settore = "Informatica", sinossi = "Una guida per diventare programmatori migliori, con consigli pratici su produttività e sviluppo software."),
            Libro(name = "Design Patterns", autore = "Erich Gamma", settore = "Informatica", sinossi = "Un classico della programmazione orientata agli oggetti, che introduce i design pattern per creare software scalabile e manutenibile."),
            Libro(name = "Artificial Intelligence: A Modern Approach", autore = "Stuart Russell", settore = "Informatica", sinossi = "Un'introduzione completa all'intelligenza artificiale, dai fondamenti teorici alle applicazioni pratiche."),
            Libro(name = "Computer Networking: A Top-Down Approach", autore = "James Kurose", settore = "Informatica", sinossi = "Un libro che spiega il funzionamento delle reti informatiche partendo dalle applicazioni fino ai livelli più bassi del networking."),
            Libro(name = "The Mythical Man-Month", autore = "Frederick P. Brooks Jr.", settore = "Informatica", sinossi = "Una raccolta di saggi sulla gestione dello sviluppo software e sulle problematiche legate alla produttività."),
            Libro(name = "Structure and Interpretation of Computer Programs", autore = "Harold Abelson", settore = "Informatica", sinossi = "Un libro fondamentale per comprendere i principi della programmazione e della progettazione di software."),
            Libro(name = "The Art of Computer Programming", autore = "Donald E. Knuth", settore = "Informatica", sinossi = "Un'opera monumentale sull'analisi degli algoritmi e sulle basi teoriche dell'informatica."),
            Libro(name = "Python Crash Course", autore = "Eric Matthes", settore = "Informatica", sinossi = "Una guida pratica e accessibile per imparare Python e applicarlo nello sviluppo di software."),
            Libro(name = "Algorithms", autore = "Robert Sedgewick", settore = "Informatica", sinossi = "Un'analisi dettagliata degli algoritmi più utilizzati in informatica, con implementazioni e spiegazioni approfondite."),
            Libro(name = "Operating System Concepts", autore = "Abraham Silberschatz", settore = "Informatica", sinossi = "Un'introduzione ai sistemi operativi, con dettagli su gestione della memoria, file system e processi."),
            Libro(name = "Code Complete", autore = "Steve McConnell", settore = "Informatica", sinossi = "Una guida pratica per lo sviluppo software che copre best practices, design e qualità del codice."),
            Libro(name = "Software Engineering", autore = "Ian Sommerville", settore = "Informatica", sinossi = "Un'introduzione ai principi e alle metodologie dell'ingegneria del software."),
            Libro(name = "Computer Architecture: A Quantitative Approach", autore = "John L. Hennessy", settore = "Informatica", sinossi = "Uno studio avanzato sull'architettura dei computer e sulle tecniche per ottimizzare le prestazioni hardware."),
            Libro(name = "Artificial Intelligence: Foundations of Computational Agents", autore = "David L. Poole", settore = "Informatica", sinossi = "Un approfondimento sull'intelligenza artificiale e sui modelli computazionali per la simulazione dell'intelligenza."),
            Libro(name = "Compilers: Principles, Techniques, and Tools", autore = "Alfred V. Aho", settore = "Informatica", sinossi = "Conosciuto come il 'Libro del Drago', è il testo di riferimento per la teoria e la pratica della compilazione."),
            Libro(name = "Java: The Complete Reference", autore = "Herbert Schildt", settore = "Informatica", sinossi = "Una guida completa per imparare e approfondire Java, con esempi pratici e dettagliati."),
            Libro(name = "Design Patterns: Elements of Reusable Object-Oriented Software", autore = "Erich Gamma", settore = "Informatica", sinossi = "Un libro chiave per lo sviluppo software, che illustra i pattern progettuali più utilizzati."),
            Libro(name = "Distributed Systems: Concepts and Design", autore = "George Coulouris", settore = "Informatica", sinossi = "Un testo di riferimento sui sistemi distribuiti, con spiegazioni su architetture, modelli e protocolli."),
            Libro(name = "Introduction to the Theory of Computation", autore = "Michael Sipser", settore = "Informatica", sinossi = "Un libro fondamentale sulla teoria della computazione, che copre automi, linguaggi formali e complessità computazionale."),
            Libro(name = "Modern Operating Systems", autore = "Andrew S. Tanenbaum", settore = "Informatica", sinossi = "Una guida moderna ai sistemi operativi, con esempi pratici e spiegazioni chiare."),
            Libro(name = "Linux Pocket Guide", autore = "Daniel J. Barrett", settore = "Informatica", sinossi = "Un manuale pratico per utilizzare i comandi più utili di Linux."),
            Libro(name = "Eloquent JavaScript", autore = "Marijn Haverbeke", settore = "Informatica", sinossi = "Un'introduzione moderna a JavaScript, con esempi interattivi e spiegazioni dettagliate."),
            Libro(name = "The C Programming Language", autore = "Brian W. Kernighan", settore = "Informatica", sinossi = "Il testo di riferimento per il linguaggio C, scritto dai suoi creatori."),
            Libro(name = "Principles of Economics", autore = "N. Gregory Mankiw", settore = "Economica", sinossi = "Un'introduzione ai principi fondamentali dell'economia, adatta sia per principianti che per studenti universitari."),
            Libro(name = "The Wealth of Nations", autore = "Adam Smith", settore = "Economica", sinossi = "Il classico fondamentale che getta le basi per la teoria economica moderna, con l'analisi del mercato e della divisione del lavoro."),
            Libro(name = "Capital in the Twenty-First Century", autore = "Thomas Piketty", settore = "Economica", sinossi = "Un'analisi della disuguaglianza economica e dei suoi effetti sul capitale e sulla società nel XXI secolo."),
            Libro(name = "Freakonomics", autore = "Steven D. Levitt", settore = "Economica", sinossi = "Un libro che esplora l'economia comportamentale, analizzando fenomeni sociali attraverso il punto di vista economico."),
            Libro(name = "The Big Short", autore = "Michael Lewis", settore = "Economica", sinossi = "Una narrazione coinvolgente delle cause e degli eventi che hanno portato alla crisi finanziaria globale del 2008."),
            Libro(name = "The General Theory of Employment, Interest, and Money", autore = "John Maynard Keynes", settore = "Economica", sinossi = "Il libro che ha fondato la macroeconomia moderna, con teorie sulle politiche economiche e la gestione della disoccupazione."),
            Libro(name = "Thinking, Fast and Slow", autore = "Daniel Kahneman", settore = "Economica", sinossi = "Un'analisi dei due sistemi di pensiero che guidano il nostro comportamento: il pensiero rapido e intuitivo, e quello lento e razionale."),
            Libro(name = "The Road to Serfdom", autore = "Friedrich Hayek", settore = "Economica", sinossi = "Un'opera che avverte dei pericoli del centralismo e delle economie pianificate, sostenendo la libertà individuale e l'economia di mercato."),
            Libro(name = "Das Kapital", autore = "Karl Marx", settore = "Economica", sinossi = "Il lavoro fondamentale che analizza la teoria del valore-lavoro e il funzionamento del capitalismo e le sue contraddizioni."),
            Libro(name = "The Intelligent Investor", autore = "Benjamin Graham", settore = "Economica", sinossi = "Una guida alla filosofia di investimento di valore, con focus sul lungo periodo e sulla gestione del rischio."),
            Libro(name = "The Origins of Political Order", autore = "Francis Fukuyama", settore = "Economica", sinossi = "Un'analisi delle origini delle istituzioni politiche, economiche e legali, dalle società tribali alle democrazie moderne."),
            Libro(name = "A Random Walk Down Wall Street", autore = "Burton G. Malkiel", settore = "Economica", sinossi = "Una panoramica dei mercati finanziari e una critica delle strategie di investimento basate sulla previsione dei mercati."),
            Libro(name = "Good to Great", autore = "Jim Collins", settore = "Economica", sinossi = "Un'analisi approfondita di come le aziende possono passare da buone a grandi, basata su anni di ricerca e studio."),
            Libro(name = "The Lean Startup", autore = "Eric Ries", settore = "Economica", sinossi = "Una metodologia per creare startup di successo, basata sull'innovazione continua, il miglioramento rapido e il feedback del cliente."),
            Libro(name = "The Black Swan", autore = "Nassim Nicholas Taleb", settore = "Economica", sinossi = "Un libro che esplora eventi rari e imprevedibili, chiamati 'Cigni Neri', e il loro impatto sul mondo e sull'economia."),
            Libro(name = "The End of Poverty", autore = "Jeffrey Sachs", settore = "Economica", sinossi = "Una discussione sulle cause della povertà globale e sulle politiche che potrebbero porvi fine."),
            Libro(name = "Globalization and Its Discontents", autore = "Joseph E. Stiglitz", settore = "Economica", sinossi = "Una critica alla globalizzazione e alle politiche economiche imposte da organizzazioni come il FMI e la Banca Mondiale."),
            Libro(name = "The New Jim Crow", autore = "Michelle Alexander", settore = "Economica", sinossi = "Un'analisi della discriminazione razziale negli Stati Uniti e del sistema di giustizia penale come strumento di oppressione."),
            Libro(name = "Debt: The First 5000 Years", autore = "David Graeber", settore = "Economica", sinossi = "Un viaggio storico attraverso il concetto di debito, esplorando come è stato una forza determinante nelle economie globali."),
            Libro(name = "The Shock Doctrine", autore = "Naomi Klein", settore = "Economica", sinossi = "Un libro che esplora come le crisi globali sono state sfruttate per attuare politiche economiche neoliberiste."),
            Libro(name = "The Power of Habit", autore = "Charles Duhigg", settore = "Economica", sinossi = "Una riflessione sul potere delle abitudini nella vita quotidiana e negli ambienti professionali ed economici."),
            Libro(name = "Rich Dad Poor Dad", autore = "Robert T. Kiyosaki", settore = "Economica", sinossi = "Un libro che mette in discussione le convinzioni tradizionali sulla finanza personale, enfatizzando l'importanza degli investimenti e dell'educazione finanziaria."),
            Libro(name = "Economics in One Lesson", autore = "Henry Hazlitt", settore = "Economica", sinossi = "Un libro che spiega in modo semplice le leggi economiche fondamentali e le loro implicazioni per la politica economica."),
            Libro(name = "The Ascent of Money", autore = "Niall Ferguson", settore = "Economica", sinossi = "Una panoramica sulla storia della finanza e del denaro, e su come hanno modellato la nostra società."),
            Libro(name = "The Four", autore = "Scott Galloway", settore = "Economica", sinossi = "Un'analisi dei quattro colossi tecnologici (Amazon, Apple, Facebook, Google) e del loro impatto sull'economia globale."),
            Libro(name = "The Millionaire Next Door", autore = "Thomas J. Stanley", settore = "Economica", sinossi = "Un'indagine su come le persone che accumulano ricchezza spesso vivono in modo frugale e discreto."),
            Libro(name = "The Elements", autore = "Euclid", settore = "Matematica", sinossi = "L'opera fondamentale di Euclide che presenta la geometria come sistema deduttivo, organizzando teoremi e postulati in una struttura logica."),
            Libro(name = "Principia Mathematica", autore = "Alfred North Whitehead, Bertrand Russell", settore = "Matematica", sinossi = "Una delle opere più influenti della logica e della matematica moderna, che cerca di derivare l'intera matematica dalla logica formale."),
            Libro(name = "The Princeton Companion to Mathematics", autore = "Timothy Gowers", settore = "Matematica", sinossi = "Una panoramica completa della matematica moderna, comprendente teorie, rami e applicazioni in vari campi."),
            Libro(name = "Gödel, Escher, Bach: An Eternal Golden Braid", autore = "Douglas Hofstadter", settore = "Matematica", sinossi = "Un'affascinante esplorazione delle connessioni tra matematica, arte e musica, con focus sulla teoria dei sistemi complessi."),
            Libro(name = "Mathematics: Its Content, Methods, and Meaning", autore = "A.D. Aleksandrov", settore = "Matematica", sinossi = "Una trattazione approfondita dei metodi e dei contenuti principali della matematica, accessibile a studenti e appassionati."),
            Libro(name = "Introduction to Probability", autore = "Dimitri P. Bertsekas, John N. Tsitsiklis", settore = "Matematica", sinossi = "Un'introduzione completa alla teoria della probabilità, utile sia per studenti che per professionisti del settore."),
            Libro(name = "Calculus", autore = "Michael Spivak", settore = "Matematica", sinossi = "Un'opera esaustiva sulla teoria del calcolo, adatta per chi desidera una comprensione profonda del calcolo infinitesimale."),
            Libro(name = "A Brief History of Time", autore = "Stephen Hawking", settore = "Matematica", sinossi = "Un libro che esplora le origini e la natura dell'universo, combinando fisica e matematica in modo accessibile."),
            Libro(name = "The Art of Computer Programming", autore = "Donald E. Knuth", settore = "Matematica", sinossi = "Una delle opere più complete e influenti in informatica, che tratta algoritmi e tecniche avanzate di programmazione con un rigoroso approccio matematico."),
            Libro(name = "Flatland: A Romance of Many Dimensions", autore = "Edwin A. Abbott", settore = "Matematica", sinossi = "Un racconto che esplora la geometria in modo innovativo, proponendo una realtà bidimensionale e le sue implicazioni filosofiche."),
            Libro(name = "The Feynman Lectures on Physics", autore = "Richard P. Feynman", settore = "Matematica", sinossi = "Le famose lezioni di Feynman che coprono concetti di fisica e matematica, spiegati in modo chiaro e coinvolgente."),
            Libro(name = "The Mathematical Theory of Communication", autore = "Claude E. Shannon, Warren Weaver", settore = "Matematica", sinossi = "Un libro fondamentale sulla teoria dell'informazione, che ha avuto un impatto profondo sulla matematica, la linguistica e l'ingegneria."),
            Libro(name = "How to Prove It: A Structured Approach", autore = "Daniel Velleman", settore = "Matematica", sinossi = "Un manuale pratico su come affrontare la prova matematica, adatto a chi è alle prime armi con la matematica formale."),
            Libro(name = "The Joy of x: A Guided Tour of Math, from One to Infinity", autore = "Steven Strogatz", settore = "Matematica", sinossi = "Un'introduzione entusiastica alla bellezza della matematica, che esplora concetti e idee in modo divertente e accessibile."),
            Libro(name = "The Code Book: The Science of Secrecy from Ancient Egypt to Quantum Cryptography", autore = "Simon Singh", settore = "Matematica", sinossi = "Un'analisi affascinante della crittografia e della matematica che sta dietro alla sicurezza delle comunicazioni."),
            Libro(name = "An Introduction to Mathematical Thinking", autore = "William P. Thurston", settore = "Matematica", sinossi = "Un'introduzione alla matematica come pensiero logico e deduttivo, non solo come calcolo e numeri."),
            Libro(name = "Mathematics for the Nonmathematician", autore = "Morris Kline", settore = "Matematica", sinossi = "Un libro che rende la matematica accessibile a chi non ha una formazione matematica, ma vuole comprendere il suo significato e impatto."),
            Libro(name = "The Metric Tensor", autore = "Michael P. Do Carmo", settore = "Matematica", sinossi = "Un'introduzione alla geometria differenziale e al concetto di tensore metrico, fondamentale per comprendere la relatività e la geometria spaziale."),
            Libro(name = "Differential Geometry of Curves and Surfaces", autore = "Manfredo P. do Carmo", settore = "Matematica", sinossi = "Un classico che introduce i concetti fondamentali della geometria differenziale, in particolare per quanto riguarda curve e superfici."),
            Libro(name = "Linear Algebra Done Right", autore = "Sheldon Axler", settore = "Matematica", sinossi = "Un'approfondita introduzione all'algebra lineare, che sfida le convenzioni tradizionali per un approccio più rigoroso e concettuale."),
            Libro(name = "Concrete Mathematics", autore = "Ronald L. Graham, Donald E. Knuth, Oren Patashnik", settore = "Matematica", sinossi = "Una raccolta di argomenti avanzati e tecniche matematiche, fondamentale per chi si occupa di matematica discreta e informatica."),
            Libro(name = "Introduction to Graph Theory", autore = "Douglas B. West", settore = "Matematica", sinossi = "Un testo introduttivo alla teoria dei grafi, un campo della matematica discreta che ha applicazioni in molte aree, tra cui informatica e ingegneria."),
            Libro(name = "Calculus of Variations", autore = "I.M. Gelfand, S.V. Fomin", settore = "Matematica", sinossi = "Un'analisi delle equazioni differenziali e del calcolo delle variazioni, con applicazioni in fisica e ingegneria."),
            Libro(name = "Number Theory", autore = "David M. Burton", settore = "Matematica", sinossi = "Un classico trattato sulla teoria dei numeri, che esplora i numeri primi, la congruenza e altri concetti fondamentali."),
            Libro(name = "Topology", autore = "James R. Munkres", settore = "Matematica", sinossi = "Un testo standard sulla topologia, che tratta i concetti fondamentali e le tecniche avanzate utilizzate nella disciplina."),
            Libro(name = "Il Contratto", autore = "René David", settore = "Giuridico", sinossi = "Un'analisi approfondita del contratto come istituto giuridico, con particolare attenzione agli elementi costitutivi e alle diverse teorie contrattuali."),
            Libro(name = "Lezioni di diritto privato", autore = "Giovanni Costa", settore = "Giuridico", sinossi = "Un manuale che affronta i principali istituti del diritto privato, come contratti, responsabilità e proprietà, con un approccio teorico e pratico."),
            Libro(name = "I principi del diritto civile", autore = "Francesco Galgano", settore = "Giuridico", sinossi = "Un'opera che esplora i principi fondamentali del diritto civile, con un focus sul sistema giuridico italiano e le sue applicazioni."),
            Libro(name = "Manuale di diritto costituzionale", autore = "Giuseppe Branca", settore = "Giuridico", sinossi = "Un manuale completo che analizza la Costituzione italiana, le sue evoluzioni storiche e le principali questioni di diritto costituzionale."),
            Libro(name = "Diritto penale", autore = "Francesco Salvia", settore = "Giuridico", sinossi = "Un trattato di diritto penale che esamina i principi e le norme applicabili alle infrazioni penali, incluse le teorie delle pene e la responsabilità penale."),
            Libro(name = "Teoria generale del diritto", autore = "Enrico Colombo", settore = "Giuridico", sinossi = "Un testo che esplora la teoria del diritto come disciplina, analizzando le sue basi, i concetti fondamentali e i modelli interpretativi."),
            Libro(name = "Diritto internazionale", autore = "Antonio Cassese", settore = "Giuridico", sinossi = "Un'opera che offre una panoramica sul diritto internazionale, coprendo le norme che regolano le relazioni tra Stati, individui e organizzazioni internazionali."),
            Libro(name = "Introduzione al diritto comparato", autore = "Pierluigi Chiassoni", settore = "Giuridico", sinossi = "Un'introduzione al diritto comparato, che esamina i sistemi giuridici di diverse nazioni e le modalità di confronto tra le diverse tradizioni giuridiche."),
            Libro(name = "Trattato di diritto civile", autore = "Vittorio Sgroi", settore = "Giuridico", sinossi = "Un trattato completo e dettagliato che analizza i principali settori del diritto civile, dalle obbligazioni al diritto di famiglia."),
            Libro(name = "Il diritto romano", autore = "Giuseppe Chiovenda", settore = "Giuridico", sinossi = "Un'opera che esplora le origini e lo sviluppo del diritto romano, mettendo in evidenza il suo impatto sulla giurisprudenza moderna."),
            Libro(name = "Manuale di diritto commerciale", autore = "Giuseppe Benacchio", settore = "Giuridico", sinossi = "Un manuale che analizza i concetti principali del diritto commerciale, come contratti, società e fallimenti, con un focus sulla legislazione italiana."),
            Libro(name = "Diritto amministrativo", autore = "Sabino Cassese", settore = "Giuridico", sinossi = "Un'analisi approfondita del diritto amministrativo, che regola le relazioni tra gli enti pubblici e i cittadini, e il funzionamento della pubblica amministrazione."),
            Libro(name = "Diritto di famiglia", autore = "Alberto L. A. Zamboni", settore = "Giuridico", sinossi = "Un trattato sul diritto di famiglia, che analizza le principali problematiche giuridiche legate al matrimonio, alla filiazione e alle relazioni familiari."),
            Libro(name = "Codice civile commentato", autore = "Giorgio Giampaoletti", settore = "Giuridico", sinossi = "Un commento approfondito al Codice Civile italiano, con spiegazioni dettagliate degli articoli e delle norme più rilevanti."),
            Libro(name = "Il diritto del lavoro", autore = "Giorgio Ghezzi", settore = "Giuridico", sinossi = "Un manuale che esplora i principi del diritto del lavoro, analizzando il contratto di lavoro, le tutele dei lavoratori e la regolamentazione delle controversie."),
            Libro(name = "Diritto penale: Parte generale", autore = "Vincenzo Spataro", settore = "Giuridico", sinossi = "Un'analisi della parte generale del diritto penale, che affronta i principi fondamentali della responsabilità penale e le sanzioni previste dal codice penale."),
            Libro(name = "Manuale di diritto tributario", autore = "Giuseppe Zizzo", settore = "Giuridico", sinossi = "Un manuale che esplora la legislazione tributaria italiana, analizzando la fiscalità, le imposte e i procedimenti relativi alla tassazione."),
            Libro(name = "I diritti fondamentali", autore = "Luigi Ferrajoli", settore = "Giuridico", sinossi = "Un'analisi dei diritti fondamentali, esplorando il concetto di giustizia e i diritti inviolabili riconosciuti a ogni individuo."),
            Libro(name = "La costituzione italiana", autore = "Massimo Luciani", settore = "Giuridico", sinossi = "Un'opera che analizza la Costituzione italiana, la sua evoluzione storica e il ruolo fondamentale che ricopre nel sistema giuridico e politico del paese."),
            Libro(name = "Diritto commerciale internazionale", autore = "Federico M. Mucci", settore = "Giuridico", sinossi = "Un testo che esplora il diritto commerciale internazionale, le norme che regolano il commercio tra Stati e le pratiche giuridiche globali."),
            Libro(name = "Lezioni di diritto pubblico", autore = "Vittorio Emanuele Orlando", settore = "Giuridico", sinossi = "Un manuale che esplora i principi del diritto pubblico, con particolare attenzione alla struttura dello Stato e alla sua organizzazione."),
            Libro(name = "Il diritto della concorrenza", autore = "Francesco Sticchi Damiani", settore = "Giuridico", sinossi = "Un trattato che analizza le leggi e le normative relative alla concorrenza, la protezione contro le pratiche anticoncorrenziali e la tutela del mercato."),
            Libro(name = "Storia del diritto medievale", autore = "Giorgio Vasari", settore = "Giuridico", sinossi = "Un'analisi storica del diritto medievale, con un focus sull'evoluzione delle istituzioni giuridiche e le norme vigenti nel Medioevo."),
            Libro(name = "La giustizia penale", autore = "Giuliano Marini", settore = "Giuridico", sinossi = "Un'opera che esplora il sistema di giustizia penale, analizzando le procedure legali, il ruolo dei giudici e il trattamento dei reati."),
            Libro(name = "I principi del diritto europeo", autore = "Rainer Arnold", settore = "Giuridico", sinossi = "Un libro che esplora i principi fondamentali del diritto europeo, le sue istituzioni e le norme che regolano l'Unione Europea e i suoi Stati membri."),
            Libro(name = "Storia della Roma antica", autore = "Theodor Mommsen", settore = "Storico", sinossi = "Un'analisi approfondita della storia della Roma antica, dalla fondazione alla sua caduta, trattando gli aspetti politici, sociali e culturali."),
            Libro(name = "Le origini della seconda guerra mondiale", autore = "A.J.P. Taylor", settore = "Storico", sinossi = "Un'interpretazione storica delle cause che hanno portato alla Seconda Guerra Mondiale, analizzando le dinamiche politiche e le scelte dei leader."),
            Libro(name = "Il Medioevo", autore = "Jacques Le Goff", settore = "Storico", sinossi = "Un'analisi del periodo medievale, esplorando la sua cultura, economia, religione e politica, e il contributo alla formazione della società moderna."),
            Libro(name = "Storia moderna", autore = "Peter N. Stearns", settore = "Storico", sinossi = "Un quadro completo della storia moderna, dal Rinascimento alla contemporaneità, esplorando le trasformazioni politiche, sociali ed economiche."),
            Libro(name = "La storia della Grecia antica", autore = "Robin Osborne", settore = "Storico", sinossi = "Un'indagine sulla Grecia antica, dalla sua nascita alla sua ascesa come civiltà dominante, con un focus sulla cultura, filosofia e politica."),
            Libro(name = "Le guerre puniche", autore = "Adrian Goldsworthy", settore = "Storico", sinossi = "Un'analisi delle guerre puniche tra Roma e Cartagine, esplorando le battaglie cruciali e gli effetti di questi conflitti sulla storia antica."),
            Libro(name = "Storia dell'Europa moderna", autore = "Norman Davies", settore = "Storico", sinossi = "Una panoramica sulla storia dell'Europa moderna, trattando eventi significativi e il loro impatto sulla configurazione politica ed economica del continente."),
            Libro(name = "La rivoluzione francese", autore = "Georges Lefebvre", settore = "Storico", sinossi = "Un'analisi della Rivoluzione francese, i suoi eventi e le sue conseguenze sulla politica europea e sulla formazione della società moderna."),
            Libro(name = "Storia della Cina", autore = "John King Fairbank", settore = "Storico", sinossi = "Un'opera che ripercorre la lunga e affascinante storia della Cina, dalle sue origini antiche ai suoi sviluppi nella Cina moderna."),
            Libro(name = "La storia delle Crociate", autore = "Steven Runciman", settore = "Storico", sinossi = "Un'analisi delle Crociate, esplorando le cause religiose e politiche, e gli effetti a lungo termine che ebbero sulle relazioni tra cristiani e musulmani."),
            Libro(name = "La storia dell'Antica Roma", autore = "Mary Beard", settore = "Storico", sinossi = "Un'affascinante ricostruzione della storia di Roma, esplorando le sue radici, la crescita e l'evoluzione di una delle più grandi civiltà della storia."),
            Libro(name = "Il risorgimento italiano", autore = "Alessandro Barbero", settore = "Storico", sinossi = "Un'analisi del Risorgimento italiano, con un focus sugli eventi, i protagonisti e le tensioni sociali che hanno portato alla formazione dello Stato italiano."),
            Libro(name = "Storia della Seconda Guerra Mondiale", autore = "Winston Churchill", settore = "Storico", sinossi = "Un racconto epico della Seconda Guerra Mondiale, scritto dal primo ministro britannico Winston Churchill, che analizza gli eventi e le decisioni cruciali del conflitto."),
            Libro(name = "La guerra dei trent'anni", autore = "C.V. Wedgwood", settore = "Storico", sinossi = "Un'analisi approfondita della Guerra dei Trent'anni, un conflitto devastante che ha segnato la storia dell'Europa nel XVII secolo."),
            Libro(name = "Storia della filosofia medievale", autore = "Etienne Gilson", settore = "Storico", sinossi = "Un'opera che esplora la filosofia medievale, le sue radici nel pensiero cristiano e le sue implicazioni per lo sviluppo della filosofia occidentale."),
            Libro(name = "La guerra civile americana", autore = "James McPherson", settore = "Storico", sinossi = "Un'analisi della Guerra Civile Americana, esaminando le sue cause, le battaglie e gli effetti sulla società e sulla politica degli Stati Uniti."),
            Libro(name = "La storia delle civiltà", autore = "Will Durant", settore = "Storico", sinossi = "Un viaggio attraverso la storia delle grandi civiltà, dal passato remoto fino ai giorni nostri, esplorando le culture che hanno plasmato il mondo."),
            Libro(name = "Il mondo antico", autore = "M.I. Finley", settore = "Storico", sinossi = "Un'opera che esplora la storia del mondo antico, concentrandosi sulla Grecia, Roma e le altre civiltà del Mediterraneo e del Vicino Oriente."),
            Libro(name = "Le origini del fascismo", autore = "Renzo De Felice", settore = "Storico", sinossi = "Un'analisi delle origini del fascismo, le sue radici ideologiche e politiche, e il suo impatto sullo sviluppo della politica europea nel XX secolo."),
            Libro(name = "La storia della Russia", autore = "Robert Service", settore = "Storico", sinossi = "Un'opera che traccia la storia della Russia, dalla sua nascita fino all'era contemporanea, esaminando le sue trasformazioni politiche e sociali."),
            Libro(name = "Storia del nazismo", autore = "Ian Kershaw", settore = "Storico", sinossi = "Un'analisi del nazismo, le sue origini, le sue ideologie e l'impatto che ha avuto sull'Europa e sul mondo."),
            Libro(name = "La storia della Grecia", autore = "N.G.L. Hammond", settore = "Storico", sinossi = "Un'opera che esplora la storia della Grecia, dalla sua formazione alle guerre che hanno plasmato la sua civiltà e il suo posto nella storia."),
            Libro(name = "Il Novecento", autore = "Eric Hobsbawm", settore = "Storico", sinossi = "Un'analisi del XX secolo, con un focus sugli eventi mondiali più significativi e le trasformazioni politiche, sociali ed economiche."),
            Libro(name = "La Storia del Cristianesimo", autore = "Jaroslav Pelikan", settore = "Storico", sinossi = "Un'opera che esplora la storia del cristianesimo, le sue origini e la sua evoluzione, con un'analisi delle sue principali correnti teologiche."),
            Libro(name = "Il Medioevo e la nascita dell'Europa", autore = "Barbara Tuchman", settore = "Storico", sinossi = "Un'analisi del Medioevo, con particolare attenzione alla nascita dell'Europa come entità politica e culturale, e le sfide che affrontò."),
            Libro(name = "La Repubblica", autore = "Platone", settore = "Filosofico", sinossi = "Un'opera fondamentale in cui Platone esplora la giustizia, la politica e la natura dell'anima attraverso il dialogo tra Socrate e altri interlocutori."),
            Libro(name = "Critica della ragion pura", autore = "Immanuel Kant", settore = "Filosofico", sinossi = "Un'opera chiave della filosofia moderna, in cui Kant analizza i limiti della conoscenza umana e la relazione tra esperienza e razionalità."),
            Libro(name = "Così parlò Zarathustra", autore = "Friedrich Nietzsche", settore = "Filosofico", sinossi = "Un'opera filosofica in forma di narrazione in cui Nietzsche esplora il concetto dell'Übermensch e critica le convenzioni morali della società."),
            Libro(name = "Il mondo come volontà e rappresentazione", autore = "Arthur Schopenhauer", settore = "Filosofico", sinossi = "Un'opera che sviluppa la teoria della volontà come forza fondamentale che dà origine a tutte le cose, presentando una visione pessimistica della realtà."),
            Libro(name = "Fenomenologia dello spirito", autore = "Georg Wilhelm Friedrich Hegel", settore = "Filosofico", sinossi = "Un'opera complessa che traccia il cammino dello spirito umano dalla percezione immediata alla consapevolezza di sé, attraversando la storia della filosofia e della cultura."),
            Libro(name = "Meditazioni", autore = "Marco Aurelio", settore = "Filosofico", sinossi = "Un'opera che raccoglie i pensieri e le riflessioni di Marco Aurelio, imperatore romano, sulla vita, il destino e la pratica della filosofia stoica."),
            Libro(name = "La genealogia della morale", autore = "Friedrich Nietzsche", settore = "Filosofico", sinossi = "Un'analisi delle origini della morale, con un'attenzione particolare alla nozione di 'buono' e 'cattivo' e il suo sviluppo nella cultura occidentale."),
            Libro(name = "La logica", autore = "Aristotele", settore = "Filosofico", sinossi = "L'opera fondante della logica, in cui Aristotele esplora i principi del ragionamento valido, l'inferenza e il sillogismo."),
            Libro(name = "Essere e tempo", autore = "Martin Heidegger", settore = "Filosofico", sinossi = "Un'opera che indaga il significato dell'essere, analizzando l'esperienza umana e la temporalità come elementi fondamentali dell'esistenza."),
            Libro(name = "Il contratto sociale", autore = "Jean-Jacques Rousseau", settore = "Filosofico", sinossi = "Un trattato politico che esplora la natura della libertà e della giustizia, proponendo la teoria del contratto sociale come fondamento della legittimità politica."),
            Libro(name = "Critica della ragion pratica", autore = "Immanuel Kant", settore = "Filosofico", sinossi = "Un'opera che esplora la moralità, definendo la legge morale come universale e autonoma, con una riflessione sull'etica e la libertà."),
            Libro(name = "Il discorso sul metodo", autore = "René Descartes", settore = "Filosofico", sinossi = "Un'opera che getta le basi del metodo scientifico moderno, incentrato sul dubbio e sulla ricerca di certezze assolute, come nel famoso 'Cogito, ergo sum'."),
            Libro(name = "La struttura delle rivoluzioni scientifiche", autore = "Thomas S. Kuhn", settore = "Filosofico", sinossi = "Un'opera che ha rivoluzionato la comprensione della scienza, descrivendo come le rivoluzioni scientifiche avvengano attraverso cambiamenti di paradigma."),
            Libro(name = "Il capitale", autore = "Karl Marx", settore = "Filosofico", sinossi = "Un'analisi economica e filosofica del capitalismo, in cui Marx esplora le dinamiche di produzione, lavoro e sfruttamento all'interno della società capitalistica."),
            Libro(name = "La società industriale e il suo futuro", autore = "Herbert Marcuse", settore = "Filosofico", sinossi = "Un'analisi critica della società industriale moderna, incentrata sulle dinamiche di alienazione e controllo sociale nella società capitalista."),
            Libro(name = "Il problema dell'induzione", autore = "David Hume", settore = "Filosofico", sinossi = "Un'opera che esplora il problema dell'induzione, mettendo in discussione la validità della conoscenza scientifica e il fondamento delle leggi naturali."),
            Libro(name = "Trattato di filosofia", autore = "Baruch Spinoza", settore = "Filosofico", sinossi = "Un'opera che espone la filosofia della natura e della mente, con un'analisi sulla razionalità e l'interconnessione di tutte le cose."),
            Libro(name = "La morale della libertà", autore = "Jean-Paul Sartre", settore = "Filosofico", sinossi = "Un'opera che esplora il concetto di libertà nella filosofia esistenzialista, ponendo l'accento sulla responsabilità individuale e la scelta."),
            Libro(name = "Filosofia della scienza", autore = "Karl Popper", settore = "Filosofico", sinossi = "Un'opera che analizza la filosofia della scienza, introducendo il concetto di falsificabilità come criterio per distinguere la scienza dalla pseudoscienza."),
            Libro(name = "La dialettica dell'illuminismo", autore = "Theodor W. Adorno", settore = "Filosofico", sinossi = "Un'analisi critica dell'Illuminismo, che esplora come la razionalità e la modernità possano condurre alla dominazione e alla repressione."),
            Libro(name = "Filosofia della storia", autore = "Georg Wilhelm Friedrich Hegel", settore = "Filosofico", sinossi = "Un'opera che descrive la storia come il progresso della libertà e della coscienza umana attraverso le contraddizioni e le risoluzioni dialettiche."),
            Libro(name = "Il pensiero occidentale", autore = "Giovanni Reale", settore = "Filosofico", sinossi = "Un'opera che traccia la storia del pensiero filosofico occidentale, dall'antichità alla filosofia contemporanea."),
            Libro(name = "La filosofia della libertà", autore = "Rudolf Steiner", settore = "Filosofico", sinossi = "Un'opera che esplora il concetto di libertà, con un focus sulla libertà spirituale e la responsabilità personale."),
            Libro(name = "Logica e linguaggio", autore = "Ludwig Wittgenstein", settore = "Filosofico", sinossi = "Un'opera che esplora le connessioni tra linguaggio e realtà, e la logica come strumento per comprendere il significato delle affermazioni."),
            Libro(name = "Introduzione alla filosofia", autore = "Niccolò Machiavelli", settore = "Filosofico", sinossi = "Un'opera che offre una panoramica sulla filosofia politica e la teoria del potere, focalizzandosi sul pensiero politico di Machiavelli."),
            Libro(name = "L'interpretazione dei sogni", autore = "Sigmund Freud", settore = "Psicologico", sinossi = "L'opera fondamentale di Freud in cui esplora il significato dei sogni e la loro connessione con l'inconscio."),
            Libro(name = "Psicologia delle masse", autore = "Sigmund Freud", settore = "Psicologico", sinossi = "Un'analisi della psicologia collettiva, esplorando come le masse influenzano il comportamento individuale."),
            Libro(name = "Il disagio della civiltà", autore = "Sigmund Freud", settore = "Psicologico", sinossi = "Un'opera in cui Freud analizza le tensioni tra le esigenze dell'individuo e le restrizioni imposte dalla società."),
            Libro(name = "Introduzione alla psicoanalisi", autore = "Sigmund Freud", settore = "Psicologico", sinossi = "Un'introduzione alla teoria psicoanalitica, in cui Freud espone i concetti fondamentali del suo pensiero."),
            Libro(name = "Psicologia e educazione", autore = "John Dewey", settore = "Psicologico", sinossi = "Un'opera che esplora l'interazione tra psicologia ed educazione, enfatizzando l'importanza dell'esperienza e dell'apprendimento attivo."),
            Libro(name = "Il comportamento dei gruppi", autore = "William McDougall", settore = "Psicologico", sinossi = "Un'analisi delle dinamiche psicologiche che influenzano il comportamento di gruppo."),
            Libro(name = "La psicologia del bambino", autore = "Jean Piaget", settore = "Psicologico", sinossi = "Un'opera che esplora lo sviluppo cognitivo e psicologico del bambino, analizzando le fasi del pensiero infantile."),
            Libro(name = "Psicologia sociale", autore = "Gordon Allport", settore = "Psicologico", sinossi = "Un'opera che esplora il comportamento sociale, l'influenza del gruppo e la psicologia delle relazioni interpersonali."),
            Libro(name = "Psicologia della personalità", autore = "Erik Erikson", settore = "Psicologico", sinossi = "Un'analisi dello sviluppo della personalità umana, esplorando le sue fasi e i conflitti che definiscono la crescita psicologica."),
            Libro(name = "Psicologia dinamica", autore = "Carl Jung", settore = "Psicologico", sinossi = "Un'opera in cui Jung esplora la psicologia analitica, concentrandosi sull'inconscio collettivo e le archetipi."),
            Libro(name = "Il comportamento umano", autore = "B.F. Skinner", settore = "Psicologico", sinossi = "Un'analisi del comportamento umano attraverso la lente del comportamentismo, enfatizzando il ruolo dell'ambiente."),
            Libro(name = "Teoria dell'apprendimento", autore = "Edward Thorndike", settore = "Psicologico", sinossi = "Un'opera che esplora le leggi dell'apprendimento e le sue applicazioni nel comportamento umano."),
            Libro(name = "Psicologia cognitiva", autore = "Ulric Neisser", settore = "Psicologico", sinossi = "Un'opera che esplora come le persone percepiscono, apprendono e memorizzano informazioni."),
            Libro(name = "Sviluppo e psicologia del bambino", autore = "Lev Vygotsky", settore = "Psicologico", sinossi = "Un'opera che esplora come il contesto sociale e culturale influenzano lo sviluppo cognitivo dei bambini."),
            Libro(name = "La mente sociale", autore = "William McDougall", settore = "Psicologico", sinossi = "Un'analisi del comportamento umano in relazione alle forze sociali e come l'individuo si integra nella società."),
            Libro(name = "Psicologia delle emozioni", autore = "Paul Ekman", settore = "Psicologico", sinossi = "Un'analisi delle emozioni umane, esplorando come vengono espressi e percepiti universalmente."),
            Libro(name = "Psicologia del lavoro", autore = "Hugo Münsterberg", settore = "Psicologico", sinossi = "Un'opera che esplora le applicazioni della psicologia nel contesto lavorativo, enfatizzando la selezione e il rendimento sul posto di lavoro."),
            Libro(name = "Il pensiero positivo", autore = "Norman Vincent Peale", settore = "Psicologico", sinossi = "Un'opera che esplora il potere del pensiero positivo e come esso può influenzare la vita di una persona."),
            Libro(name = "Psicoterapia", autore = "Carl Rogers", settore = "Psicologico", sinossi = "Un'opera che espone le basi della psicoterapia centrata sulla persona, enfatizzando il ruolo della relazione terapeutica."),
            Libro(name = "Psicologia dell'adolescenza", autore = "David Elkind", settore = "Psicologico", sinossi = "Un'analisi dello sviluppo psicologico durante l'adolescenza, affrontando tematiche come l'identità e la crescita sociale."),
            Libro(name = "La psiche e il corpo", autore = "Sigmund Freud", settore = "Psicologico", sinossi = "Un'opera in cui Freud esplora la connessione tra la mente e il corpo e il ruolo dell'inconscio nelle malattie psicosomatiche."),
            Libro(name = "Teoria della motivazione", autore = "Abraham Maslow", settore = "Psicologico", sinossi = "Un'opera che espone la teoria della motivazione umana, inclusa la famosa piramide dei bisogni."),
            Libro(name = "Psicologia dell'apprendimento", autore = "B.F. Skinner", settore = "Psicologico", sinossi = "Un'opera che esplora i principi del comportamentismo e il modo in cui gli individui apprendono attraverso rinforzi e punizioni."),
            Libro(name = "Psicologia della salute", autore = "Robert Sapolsky", settore = "Psicologico", sinossi = "Un'analisi delle interazioni tra psicologia, salute fisica e stress, con un focus sulle risposte fisiologiche agli stimoli psicologici."),
            Libro(name = "Psicologia della comunicazione", autore = "Wilbur Schramm", settore = "Psicologico", sinossi = "Un'opera che esplora i meccanismi psicologici alla base della comunicazione, con un'analisi dei processi di ricezione e interpretazione dei messaggi."),
            Libro(name = "Pedagogia", autore = "Giovanni Gentile", settore = "Pedagogico", sinossi = "Un'opera fondamentale che esplora la teoria pedagogica e l'importanza dell'educazione nella società."),
        Libro(name = "La scuola come comunità", autore = "John Dewey", settore = "Pedagogico", sinossi = "Un'opera che sottolinea l'importanza della scuola come luogo di crescita e comunità per il miglioramento sociale."),
        Libro(name = "L'educazione come libertà", autore = "Paulo Freire", settore = "Pedagogico", sinossi = "Un libro che esplora la libertà educativa come un processo di empowerment per gli studenti."),
        Libro(name = "La pedagogia del futuro", autore = "Maria Montessori", settore = "Pedagogico", sinossi = "Un'opera in cui Montessori esplora la pedagogia innovativa e l'approccio individualizzato per l'educazione dei bambini."),
        Libro(name = "La pedagogia scientifica", autore = "Gianfranco Zavalloni", settore = "Pedagogico", sinossi = "Un'analisi della pedagogia come scienza che deve essere basata sull'osservazione e sull'esperienza."),
        Libro(name = "L'arte dell'insegnamento", autore = "Richard Sennett", settore = "Pedagogico", sinossi = "Un'opera che esplora il ruolo dell'insegnante come artigiano e la sua capacità di lavorare in modo creativo con gli studenti."),
        Libro(name = "Il diritto all'educazione", autore = "Nel Noddings", settore = "Pedagogico", sinossi = "Un libro che esplora la teoria dell'educazione e i diritti degli studenti all'istruzione."),
        Libro(name = "L'educazione dell'infanzia", autore = "Jean Piaget", settore = "Pedagogico", sinossi = "Un'opera che esplora lo sviluppo cognitivo del bambino, con un focus sull'educazione infantile."),
        Libro(name = "Teoria pedagogica", autore = "Erik Erikson", settore = "Pedagogico", sinossi = "Un'analisi dello sviluppo psicologico dell'individuo e il suo impatto sull'educazione."),
        Libro(name = "La scuola come agenzia sociale", autore = "Durkheim Emile", settore = "Pedagogico", sinossi = "Un'analisi del ruolo della scuola come agenzia di socializzazione e trasmissione culturale."),
        Libro(name = "Educare alla vita", autore = "Giorgio Santi", settore = "Pedagogico", sinossi = "Un'opera che esplora come l'educazione possa essere un mezzo per preparare gli studenti alla vita e alla società."),
        Libro(name = "La formazione degli insegnanti", autore = "Robert L. Ebel", settore = "Pedagogico", sinossi = "Un'opera che esplora come formare gli insegnanti affinché siano preparati a rispondere alle esigenze della classe."),
        Libro(name = "La psicologia dell'insegnamento", autore = "Lev Vygotsky", settore = "Pedagogico", sinossi = "Un'analisi dei processi psicologici che influenzano l'insegnamento e l'apprendimento."),
        Libro(name = "Metodi educativi", autore = "Gianfranco Zavalloni", settore = "Pedagogico", sinossi = "Un'opera che esplora le diverse metodologie educative e il loro impatto sull'insegnamento."),
        Libro(name = "Pedagogia della libertà", autore = "Paulo Freire", settore = "Pedagogico", sinossi = "Un'opera che esplora l'importanza della libertà educativa e della consapevolezza critica in ogni processo di apprendimento."),
        Libro(name = "Pedagogia e cultura", autore = "John Dewey", settore = "Pedagogico", sinossi = "Un'analisi del rapporto tra pedagogia e cultura, con un focus sull'influenza della cultura nella formazione educativa."),
        Libro(name = "L'educazione integrale", autore = "Maria Montessori", settore = "Pedagogico", sinossi = "Un'opera che esplora l'educazione integrale, enfatizzando lo sviluppo fisico, emotivo e intellettuale del bambino."),
        Libro(name = "Educazione e crescita", autore = "Jean-Jacques Rousseau", settore = "Pedagogico", sinossi = "Un'opera che esplora la visione di Rousseau sull'educazione naturale e il suo impatto sullo sviluppo umano."),
        Libro(name = "Il futuro dell'educazione", autore = "Paulo Freire", settore = "Pedagogico", sinossi = "Un'opera che esplora le sfide future dell'educazione e la necessità di un approccio critico e liberatorio."),
        Libro(name = "Psicopedagogia", autore = "Sabrina D'Angelo", settore = "Pedagogico", sinossi = "Un'analisi della psicopedagogia, che combina la psicologia e la pedagogia per affrontare le difficoltà educative degli studenti."),
        Libro(name = "Educazione e inclusività", autore = "Sally Brown", settore = "Pedagogico", sinossi = "Un'opera che esplora l'importanza dell'inclusività nell'educazione, con un focus sull'integrazione di studenti con diverse abilità."),
        Libro(name = "Sociologia dell'educazione", autore = "Pierre Bourdieu", settore = "Pedagogico", sinossi = "Un'analisi sociologica dell'educazione, esplorando come l'educazione sia influenzata da fattori sociali ed economici."),
        Libro(name = "La pedagogia del bambino", autore = "Maria Montessori", settore = "Pedagogico", sinossi = "Un'opera che esplora il metodo Montessori, focalizzandosi sull'educazione e sullo sviluppo del bambino."),
        Libro(name = "L'insegnamento come arte", autore = "Maxine Greene", settore = "Pedagogico", sinossi = "Un'opera che esplora il ruolo dell'insegnamento come forma d'arte e creatività."),
        Libro(name = "La didattica delle lingue", autore = "David Nunan", settore = "Pedagogico", sinossi = "Un'opera che esplora i metodi e le teorie didattiche per insegnare le lingue straniere."))

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
              try {
                  libri.forEach{    it ->
                      dbViewModel.aggiungiLibro(it)
                  }
              }catch (e: Exception){
                  Log.e("BibliotecaDEBUG", "Errore durante l'inserimento dei libri", e)
              }
            }
            withContext(Dispatchers.Main) {
                // Mostra un messaggio di conferma
                Toast.makeText(this@Biblioteca, "Benvenuto nell'Area Biblioteca", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
