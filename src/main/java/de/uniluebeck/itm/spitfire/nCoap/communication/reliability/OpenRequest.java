/**
* Copyright (c) 2012, Oliver Kleine, Institute of Telematics, University of Luebeck
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
* following conditions are met:
*
* - Redistributions of source code must retain the above copyright notice, this list of conditions and the following
* disclaimer.
* - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
* following disclaimer in the documentation and/or other materials provided with the distribution.
* - Neither the name of the University of Luebeck nor the names of its contributors may be used to endorse or promote
* products derived from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
* INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
* ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
* INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
* GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
* LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
* OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package de.uniluebeck.itm.spitfire.nCoap.communication.reliability;

import de.uniluebeck.itm.spitfire.nCoap.message.Message;

import java.net.InetSocketAddress;

/**
 * Created by IntelliJ IDEA.
 * User: olli
 * Date: 30.01.12
 * Time: 14:22
 * To change this template use File | Settings | File Templates.
 */
public abstract class OpenRequest{

    private InetSocketAddress rcptSocketAddress;
    private Message message;
    protected long nextTransmitTime;


    public OpenRequest(InetSocketAddress rcptSocketAddress, Message message){
        this.message = message;
        this.rcptSocketAddress = rcptSocketAddress;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public long getNextTransmitTime(){
        return nextTransmitTime;
    }

    public InetSocketAddress getRcptSocketAddress() {
        return rcptSocketAddress;
    }

    public void setRcptSocketAddress(InetSocketAddress rcptSocketAddress) {
        this.rcptSocketAddress = rcptSocketAddress;
    }

    public abstract void setNextTransmitTime();

}