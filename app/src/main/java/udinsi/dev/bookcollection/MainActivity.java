package udinsi.dev.bookcollection;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import udinsi.dev.bookcollection.model.Book;

public class MainActivity extends AppCompatActivity {
    Button add;
    Button delAll;
    EditText search;
    RecyclerView rvBook;
    TextView sort;
    Dialog dialog;
    Realm realm;

    BookAdapter bookAdapter;
    private List<Book> resultTable = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();

        add = findViewById(R.id.buttonAdd);
        delAll = findViewById(R.id.buttonDelAll);
        search = findViewById(R.id.etSearch);
        rvBook = findViewById(R.id.rvBook);
        sort = findViewById(R.id.tvSort);

        resultTable = realm.where(Book.class).findAll();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvBook.addItemDecoration(dividerItemDecoration);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvBook.setLayoutManager(llm);

        bookAdapter = new BookAdapter(resultTable);
        rvBook.setAdapter(bookAdapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messagePopUp("");
            }
        });
    }

    public void messagePopUp(final String link) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.popup_dialog_export, null, false);

        Button btok = view.findViewById(R.id.btn_ok);
        final EditText code = view.findViewById(R.id.etCode);
        final EditText isbn = view.findViewById(R.id.isbn);
        final EditText title = view.findViewById(R.id.title);
        final EditText desc = view.findViewById(R.id.desc);
        final EditText date = view.findViewById(R.id.date);
        final EditText price = view.findViewById(R.id.price);
        final Spinner category = view.findViewById(R.id.cat);
        final String finalCat = category.getSelectedItem().toString();
        btok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                final Book book = new Book();
                book.setCode(code.getText().toString());
                book.setIsbn(isbn.getText().toString());
                book.setTitle(title.getText().toString());
                book.setCategory(finalCat);
                book.setDesc(desc.getText().toString());
                book.setDate(date.getText().toString());
                book.setPrice(Integer.parseInt(price.getText().toString()));

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealmOrUpdate(book);
                    }
                });

                resultTable = realm.where(Book.class).findAll();
                bookAdapter.notifyDataSetChanged();
            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private class BookAdapter extends RecyclerView.Adapter<MainActivity.ItemHolder>{
        List<Book> books;

        public BookAdapter(List<Book> books){
            this.books = books;
        }
        @NonNull
        @Override
        public MainActivity.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_book, parent, false);
            MainActivity.ItemHolder item = new MainActivity.ItemHolder(view);
            return item;
        }

        @Override
        public void onBindViewHolder(@NonNull MainActivity.ItemHolder holder, int position) {
            Book book = books.get(position);
            holder.tvBook.setText(book.getTitle());
        }

        @Override
        public int getItemCount() {
            return books.size();
        }
    }

    private class ItemHolder extends RecyclerView.ViewHolder{
        TextView tvBook;
        ImageView detail;
        ImageView delete;
        CheckBox cb;
        public ItemHolder(View itemView) {
            super(itemView);
            this.tvBook = itemView.findViewById(R.id.tvBook);
            this.detail = itemView.findViewById(R.id.detail);
            this.delete = itemView.findViewById(R.id.delete);
            this.cb = itemView.findViewById(R.id.cbBook);
        }
    }
}
