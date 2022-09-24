package fr.uha.contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class ContactAdapter extends BaseAdapter {

    private Context context;
    ArrayList<Contact> liste = new ArrayList<Contact>();
    ArrayList<Contact> searchContacts;
    private CustomFilter filter;

    public ContactAdapter(Context context) {
        this.context = context;
        this.searchContacts = this.liste;
    }

    public void ajout() {
        Contact perso1 = new Contact("BART", "Alex", "00 00 01");
        Contact perso2 = new Contact("BARTHELME", "Alexandre", "00 00 02");
        Contact perso3 = new Contact("Munch", "Luc", "00004");
        Contact perso4 = new Contact("ZNTONY ", "Sebastien", "00007");
        liste.add(perso1);
        liste.add(perso2);
        liste.add(perso3);
        liste.add(perso4);
    }


    @Override
    public int getCount() {
        return liste.size();
    }

    @Override
    public Object getItem(int position) {
        return liste.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.contact_activity, null);
        }

        Contact val = liste.get(position);

        TextView itemView = v.findViewById(R.id.textView);
        itemView.setText(val.getNOM());
        TextView itemView2 = v.findViewById(R.id.textView2);
        itemView2.setText(val.getPRENOM());


        return v;
    }


    public int size() {
        return liste.size();
    }

    public Contact get(int index) {
        return liste.get(index);
    }

    public Contact set(int index, Contact element) {
        return liste.set(index, element);
    }


    public void addI(int index, Contact element) {
        liste.add(index, element);
    }

    public boolean remove(Contact contact) {
        return liste.remove(contact);
    }

    public boolean add(Contact contact) {
        return liste.add(contact);
    }

    public Contact remove(int index) {
        return liste.remove(index);
    }

    public static Comparator<Contact> ComparatorNom = new Comparator<Contact>() {
        @Override
        public int compare(Contact o1, Contact o2) {
            return o1.getNOM().compareTo(o2.getNOM());
        }
    };


    class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toLowerCase();
                ArrayList<Contact> filters = new ArrayList<>();

                for (int i = 0; i < searchContacts.size(); i++) {
                    if (searchContacts.get(i).toString().toLowerCase().contains(constraint)) {
                        filters.add(searchContacts.get(i));
                    }
                }

                results.count = filters.size();
                results.values = filters;

            } else {
                results.count = searchContacts.size();
                results.values = searchContacts;

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            liste = (ArrayList<Contact>) results.values;
            notifyDataSetChanged();
        }
    }


    public Filter getFilter() {
        if (this.filter == null) {
            this.filter = new CustomFilter();
        }
        return this.filter;
    }
}