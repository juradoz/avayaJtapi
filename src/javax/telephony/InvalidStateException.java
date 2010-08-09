 package javax.telephony;
 
 public class InvalidStateException extends Exception
 {
   private static final long serialVersionUID = 1L;
   private int _type;
   private int _state;
   private Object _object;
   public static final int PROVIDER_OBJECT = 0;
   public static final int CALL_OBJECT = 1;
   public static final int CONNECTION_OBJECT = 2;
   public static final int TERMINAL_OBJECT = 3;
   public static final int ADDRESS_OBJECT = 4;
   public static final int TERMINAL_CONNECTION_OBJECT = 5;
 
   public InvalidStateException(Object object, int type, int state)
   {
     this._type = type;
     this._object = object;
     this._state = state;
   }
 
   public InvalidStateException(Object object, int type, int state, String s)
   {
     super(s);
     this._type = type;
     this._object = object;
     this._state = state;
   }
 
   public int getObjectType()
   {
     return this._type;
   }
 
   public Object getObject()
   {
     return this._object;
   }
 
   public int getState()
   {
     return this._state;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.InvalidStateException
 * JD-Core Version:    0.5.4
 */