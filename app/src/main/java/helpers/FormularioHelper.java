package helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.calculadora.trabalhofinalmobile.R;
import activities.FormularioActivity;
import model.Aluno;

public class FormularioHelper {
    private EditText nome;
    private EditText telefone;
    private EditText site;
    private SeekBar nota;
    private EditText endereco;
    private ImageView foto;
    private Aluno aluno;

    public FormularioHelper(FormularioActivity activity) {

        nome = (EditText) activity.findViewById(R.id.nome);
        telefone = (EditText) activity.findViewById(R.id.telefone);
        site = (EditText) activity.findViewById(R.id.site);
        nota = (SeekBar) activity.findViewById(R.id.nota);
        endereco = (EditText) activity.findViewById(R.id.endereco);
        foto = (ImageView) activity.findViewById(R.id.foto);
        aluno = new Aluno();
    }

    public Aluno pegaAlunoDoFormulario() {

        aluno.setNome(nome.getEditableText().toString());
        aluno.setEndereco(endereco.getEditableText().toString());
        aluno.setSite(site.getEditableText().toString());
        aluno.setTelefone(telefone.getEditableText().toString());
        aluno.setNota(Double.valueOf(nota.getProgress()));

        return aluno;
    }

    public void colocaAlunoNoFormulario(Aluno aluno) {
        this.aluno = aluno;

        nome.setText(aluno.getNome());
        telefone.setText(aluno.getTelefone());
        site.setText(aluno.getSite());
        endereco.setText(aluno.getEndereco());
        nota.setProgress((int) aluno.getNota());

        if (aluno.getFoto() != null) {
            this.carregaImagem(aluno.getFoto());
        }
    }

    public ImageView getBotaoImagem() {
        return foto;
    }

    public void carregaImagem(String localArquivoFoto) {
        Bitmap imagemFoto = BitmapFactory.decodeFile(localArquivoFoto);
        Bitmap imagemFotoReduzida = Bitmap.createScaledBitmap(imagemFoto, 100, 100, true);

        aluno.setFoto(localArquivoFoto);
        foto.setImageBitmap(imagemFotoReduzida);
    }

}


