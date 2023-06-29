package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoDAO extends SQLiteOpenHelper {
    //configurações de versão da base de dados que será criada
    private static final int VERSAO = 1;
    //nome da tabela que será criada e utilizada nos scripts
    private static final String TABELA = "CadastroAlunos";
    //nome da base de dados
    private static final String DATABASE = "CadastroBSN";
    //colunas da tabela ser criada no script
    private static final String[] COLUNAS = { "id", "nome",
            "telefone", "endereco", "site", "nota", "foto" };

    public AlunoDAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //comando DDL para criação da tabela SQLite
        String ddl = "CREATE TABLE " + TABELA + " (id INTEGER PRIMARY KEY, "
                + " nome TEXT UNIQUE NOT NULL, telefone TEXT, endereco TEXT, "
                + " site TEXT, nota REAL, foto TEXT);";

        db.execSQL(ddl);

    }

    @Override
    //Esse método é executado automaticamente pelo sistema android, quando ele detectar que a versão do banco de dados mudou
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABELA;
        db.execSQL(sql);
        onCreate(db);
    }

    //método utilizado para inserir um novo cadastro no banco de dados SQLite. Esse método irá receber uma instância do Modelo Aluno por parâmetro.
    public void insere(Aluno aluno) {
        ContentValues values = new ContentValues();

        values.put("nome", aluno.getNome());
        values.put("telefone", aluno.getTelefone());
        values.put("endereco", aluno.getEndereco());
        values.put("site", aluno.getSite());
        values.put("nota", aluno.getNota());
        values.put("foto", aluno.getFoto());

        //A linha abaixo é responsável para solicitar ao Android que salve as informações do aluno. Esse método é possível de ser executado pois a classe extends SQLiteOpenHelper, que é uma superclasse do android que abstrai várias funcionalidade de acesso a dados no banco de dados SQLite.
        getWritableDatabase().insert(TABELA, null, values);
    }
    //método utilizado para listar os Alunos cadastrados na base de dados SQLite. Este método realiza uma consulta na base de dados e devolve uma ArrayList com os alunos recuperados.
    public List<Aluno> getLista() {

        //ArraList criado para o retorno dos dados
        List<Aluno> alunos = new ArrayList<>();

        //no Android é criado um cursor utilizando a superclasse, com a finalidade de buscar os dados da base de dados SQLite
        Cursor c = getWritableDatabase().query(TABELA, COLUNAS, null,
                null, null, null, null);

        //após abrir o cursor, deve-se percorrê-lo para popular o ArrayList que será retornado.
        while (c.moveToNext()) {
            Aluno aluno = new Aluno();

            aluno.setId(c.getLong(0));
            aluno.setNome(c.getString(1));
            aluno.setTelefone(c.getString(2));
            aluno.setEndereco(c.getString(3));
            aluno.setSite(c.getString(4));
            aluno.setNota(c.getDouble(5));
            aluno.setFoto(c.getString(6));

            alunos.add(aluno);
        }

        //após percorrer o cursor, deve-se fechar o mesmo.
        c.close();

        //por fir retorna-se o ArrayList com os dados recuperados
        return alunos;
    }

    //abaixo o método DAO para deletar alunos cadastrados na base de dados.
    public void deletar(Aluno aluno) {
        String[] args = {aluno.getId().toString()};
        getWritableDatabase().delete(TABELA, "id=?", args);
    }

    //método utilizado para realizar alterações nos registros do banco de dados SQLite. Essemétodo irá receber um objeto aluno por parâmetro e então realizar a alteração na base de dados.
    public void altera(Aluno aluno) {

        //Para realizar a alteraçãoao dos dados na base de dados SQLite é necessário
        ContentValues values = new ContentValues();

        values.put("nome", aluno.getNome());
        values.put("telefone", aluno.getTelefone());
        values.put("endereco", aluno.getEndereco());
        values.put("site", aluno.getSite());
        values.put("nota", aluno.getNota());
        values.put("foto", aluno.getFoto());

        String[] args = {aluno.getId().toString()};
        getWritableDatabase().update(TABELA, values, "id=?", args );
    }

    public boolean isAluno(String telefone) {
        Cursor rawQuery = getReadableDatabase().rawQuery(
                "SELECT telefone from " + TABELA + " WHERE telefone = ?",
                new String[] { telefone });

        int total = rawQuery.getCount();
        rawQuery.close();

        return total > 0;
    }

}

