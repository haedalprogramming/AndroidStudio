package org.techtown.todolist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private static final String TAG = "NoteAdapter";

    //todolist아이템이 들어갈 배열
    ArrayList<Note> items = new ArrayList<>();



    //todo_item.xml을 인플레이션
    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.todo_item, parent, false);


        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        Note item = items.get(position);
        holder.setItem(item);
        holder.setLayout();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    //ViewHolder의 역할을 하는 클래스
    static class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout layoutTodo;
        CheckBox checkBox;
        Button deleteButton;

        public ViewHolder(View itemView){
            super(itemView);

            layoutTodo = itemView.findViewById(R.id.layoutTodo);
            checkBox = itemView.findViewById(R.id.checkBox);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            //버튼 클릭 시 SQLite에서 데이터 삭제
            deleteButton.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    //CheckBox의 String 가져오기
                    String TODO = (String) checkBox.getText();
                    deleteToDo(TODO);
                    Toast.makeText(v.getContext(),"삭제되었습니다.",Toast.LENGTH_SHORT).show();
                }


                Context context;

                private void deleteToDo(String TODO){
                    //테이블을 삭제하는 sql문 delete...
                    String deleteSql = "delete from " + NoteDatabase.TABLE_NOTE + " where " + "  TODO = '" + TODO+"'";
                    NoteDatabase database = NoteDatabase.getInstance(context);
                    //삭제하는 sql문 실행
                    database.execSQL(deleteSql);
                }
            });


        }
        //EditText에서 입력받은 checkBox의 텍스트를 checkBox의 Text에 넣을 수 있게 하는 메서드
        public void setItem(Note item){
            checkBox.setText(item.getTodo());
        }

        //아이템들을 담은 LinearLayout을 보여주게하는 메서드
        public void setLayout(){
            layoutTodo.setVisibility(View.VISIBLE);
        }
    }

    //배열에 있는 item들을 가리킴
    public void setItems(ArrayList<Note> items){
        this.items = items;
    }

}
