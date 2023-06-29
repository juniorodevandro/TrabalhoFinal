package adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.calculadora.trabalhofinalmobile.R;
import model.Aluno;

import java.util.List;

@SuppressLint("ViewHolder")
public class ListaAlunosAdapter extends BaseAdapter {
    private final List<Aluno> alunos;
    private final Activity activity;

    public ListaAlunosAdapter(Activity activity, List<Aluno> alunos) {
        this.activity = activity;
        this.alunos = alunos;
    }

    public long getItemId(int posicao) {
        return alunos.get(posicao).getId();
    }

    public Object getItem(int posicao) {
        return alunos.get(posicao);
    }

    public int getCount() {
        return alunos.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.item, null);

        if (position % 2 == 0) {
            view.setBackgroundColor(activity.getResources().
                    getColor(R.color.linha_par));
        }

        Aluno aluno = alunos.get(position);

        TextView nome = (TextView) view.findViewById(R.id.nome);
        nome.setText(aluno.getNome());

        Bitmap bm;

        if (aluno.getFoto() != null) {
            bm = BitmapFactory.decodeFile(aluno.getFoto());
        } else {
            bm = BitmapFactory.decodeResource(activity.getResources(),
                    R.drawable.noimage);
        }

        bm = Bitmap.createScaledBitmap(bm, 100, 100, true);

        ImageView foto = (ImageView) view.findViewById(R.id.foto);
        foto.setImageBitmap(bm);

        return view;
    }
}

