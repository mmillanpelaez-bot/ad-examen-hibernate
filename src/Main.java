import model.*;
import service.*;
import util.ExportImportUtil;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ServicioComic sComic = new ServicioComic();
        ServicioAutor sAutor = new ServicioAutor();
        ServicioProtagonista sProta = new ServicioProtagonista();

        // 1. Instanciar Autores y Protagonistas (reutilizando instancias para evitar duplicados)
        Autor stan = new Autor("Stan", "Lee", "estadounidense");
        Autor alan = new Autor("Alan", "Moore", "británica");
        Autor frank = new Autor("Frank", "Miller", "estadounidense");
        Autor herge = new Autor("Hergé", "Remi", "belga");

        Protagonista spidey = new Protagonista("Spider-Man", "superforza e axilidade");
        Protagonista batman = new Protagonista("Batman", "diñeiro");
        Protagonista superman = new Protagonista("Superman", "superforza e batSubvención");
        Protagonista wonder = new Protagonista("Wonder Woman", "superforza e batSubvención");
        Protagonista tintin = new Protagonista("Tintín", "intelixencia e capacidade de investigación");
        Protagonista daredevil = new Protagonista("Daredevil", "case tódo-los sentidos sobrehumanos");

        // 2. Insertar Comics (inserta en cascada Autores y Protagonistas)
        List<Comic> comics = Arrays.asList(
                new Comic("The Amazing Spider-Man", 1963, stan, spidey),
                new Comic("Batman: The Killing Joke", 1988, alan, batman),
                new Comic("Superman: The Man of Steel", 1986, frank, superman),
                new Comic("Wonder Woman: Gods and Mortals", 1987, alan, wonder),
                new Comic("The Adventures of Tintin", 1929, herge, tintin),
                new Comic("Daredevil: Born Again", 1986, stan, daredevil),
                new Comic("Batman: Year One", 1987, frank, batman),
                new Comic("Spider-Man: Blue", 2002, stan, spidey)
        );
        sComic.inserirComics(comics);

        // 3. Listar inicial
        sComic.listarPorAutor();

        // 4. [Recu Ficheiros] Leer fichero "datos" (línea 0: autor, línea 1: protagonista)
        List<String> lineas = ExportImportUtil.readFicheiro("datos");
        String autorALeer = lineas.get(0); // "Stan Lee"
        String protaALeer = lineas.get(1); // "Spider-man"

        // 5. Actualizar Autor + [Recu Serialización] + [Recu XML]
        Autor autorActualizado = sAutor.actualizarNomePorNomeCompleto(autorALeer, "Satan", "Leee");
        ExportImportUtil.serializar(autorActualizado, "autor.dat");
        ExportImportUtil.autorToXML(autorActualizado, "autor.xml");

        // 6. Actualizar Protagonista
        sProta.actualizarNome(protaALeer, "Spooder-Mon");

        // 7. Listar actualizado
        sComic.listarPorAutor();

        // 8. Borrar todo (RESPETANDO ORDEN DE CLAVES FORÁNEAS: primero Comic)
        sComic.borrarTodos();
        sAutor.borrarTodos();
        sProta.borrarTodos();

        // 9. Listar vacío
        sComic.listarPorAutor();
    }
}