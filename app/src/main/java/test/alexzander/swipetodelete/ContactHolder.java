package test.alexzander.swipetodelete;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by AlexZandR on 4/24/17.
 */

public class ContactHolder extends RecyclerView.ViewHolder {

    public boolean isPendingDelete = false;
    public TextView deletedName;
    public TextView name;
    public TextView phone;
    public View progressIndicator;
    public View contactContainer;
    public View undoContainer;
    public View undoData;


    public ContactHolder(final View itemView, final UndoClickListener listener) {
        super(itemView);
        deletedName = (TextView) itemView.findViewById(R.id.user_name_deleted);
        name = (TextView) itemView.findViewById(R.id.user_name);
        phone = (TextView) itemView.findViewById(R.id.user_phone_number);
        contactContainer = itemView.findViewById(R.id.contact_container);
        undoContainer = itemView.findViewById(R.id.undo_container);
        undoData = itemView.findViewById(R.id.undo_data);
        itemView.findViewById(R.id.button_undo).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onUndoClick(getAdapterPosition());
                    }
                });
        progressIndicator = itemView.findViewById(R.id.progress_indicator);
    }

    View getTopContainer() {
        if (isPendingDelete) {
            return undoContainer;
        } else {
            return contactContainer;
        }
    }
}
