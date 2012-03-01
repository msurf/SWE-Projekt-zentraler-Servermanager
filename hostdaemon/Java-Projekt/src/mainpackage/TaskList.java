package mainpackage;

import java.util.ArrayList;

import javax.swing.AbstractListModel;


public class TaskList<E> extends AbstractListModel{

private static final long serialVersionUID = 1L;
	
	private ArrayList<E> _delegate = new ArrayList<E>();
	
	@Override
	public synchronized Object getElementAt(int arg0) {
		return (E)this._delegate.get(arg0);
	}//getElementAt()

	@Override
	public synchronized int getSize() {
		return this._delegate.size();
	}//getSize
	
	public synchronized void add(E e){
		 int index = this._delegate.size();
	        this._delegate.add(e);
	        fireIntervalAdded(this, index, index);
	}//add()
	
	public synchronized void delete(int index){
		if(index < 0 || index >= this._delegate.size())
		{
			//throw new IndexOutOfBoundsException("Index out of Bounds!");
		}//if
		else{
			this._delegate.remove(index);
			fireIntervalRemoved(this, index, index);
		}//else
	}//delete()
	
	public synchronized void delete(){
		this._delegate.remove(0);
		fireIntervalRemoved(this, 0, 0);
	}//delete()

}
